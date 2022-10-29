package com.badsha.currencyconversion.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.badsha.currencyconversion.common.Constants
import com.badsha.currencyconversion.common.Resource
import com.badsha.currencyconversion.domain.extension.roundTwoDeciaml
import com.badsha.currencyconversion.domain.model.Currency
import com.badsha.currencyconversion.domain.model.Rate
import com.badsha.currencyconversion.domain.use_case.ChargeCalculationUseCases
import com.badsha.currencyconversion.domain.use_case.CurrencyUseCases
import com.badsha.currencyconversion.domain.use_case.get_rate.GetRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRateUseCase: GetRateUseCase,
    private val currencyUseCases: CurrencyUseCases,
    private val chargeCalculationUseCases: ChargeCalculationUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _dialogState = mutableStateOf(HomeDialogState(false, ""))
    val dialogState: State<HomeDialogState> = _dialogState

    val currencyRates: MutableMap<String, List<Rate>> = mutableMapOf()
    val availableCurrencies: MutableState<List<Currency>> = mutableStateOf(emptyList())

    var sellingCurrency: MutableState<Currency?> = mutableStateOf(null)
    var sellableCurrencies: MutableState<List<Currency>> = mutableStateOf(emptyList())
    val sellAmount: MutableState<Double?> = mutableStateOf(null)

    var buyingCurrency: MutableState<Rate?> = mutableStateOf(null)
    var buyableCurrencies: MutableState<List<Rate>> = mutableStateOf(emptyList())
    val buyAmount: MutableState<Double?> = mutableStateOf(null)

    val chargeAmount: MutableState<Double> = mutableStateOf(0.0)
    var chargeRate: Double = 0.0
    var conversionRate: Double = 0.0

    init {
        viewModelScope.launch {
            preloadBaseCurrency()
            getAvailableCurrencies()
        }
    }

    private fun showSuccessDialog(title: String, message: String) {
        _dialogState.value = HomeDialogState(true, title = title, message = message)
    }

    fun hideSuccessDialog() {
        _dialogState.value = HomeDialogState(false, message = "")
    }


    private fun calculateChargeForOffers(): Double {
        var baseCharge = ((sellAmount.value ?: 0.0) * chargeRate / 100).roundTwoDeciaml()
        baseCharge = chargeCalculationUseCases.chargeFreeOnTwoHundredSell.invoke(
            baseCharge, sellAmount.value?.roundTwoDeciaml() ?: 0.0
        )

        availableCurrencies.value.firstOrNull {
            it.name == sellingCurrency.value?.name
        }?.let {
            baseCharge = chargeCalculationUseCases.chargeFreeForFirstFive.invoke(baseCharge, it)
        }
        return baseCharge.roundTwoDeciaml()
    }

    fun resetInput() {
        sellAmount.value = null
        buyAmount.value = null
        chargeAmount.value = 0.0
    }

    fun onSellAmountChange(amount: String) {
        try {
            val validatedAmount = amount.replace("..", ".").toDouble().roundTwoDeciaml()
            if (validatedAmount == 0.0) {
                resetInput()
            } else {
                sellAmount.value = validatedAmount
                sellAmount.value?.let {
                    buyAmount.value = (it * conversionRate).roundTwoDeciaml()
                    chargeAmount.value = calculateChargeForOffers()
                }
            }
        } catch (e: Exception) {
            resetInput()
        }
    }

    fun onBuyAmountChange(amount: String) {
        try {
            val validatedAmount = amount.replace("..", ".").toDouble().roundTwoDeciaml()
            if (validatedAmount == 0.0) {
                resetInput()
            } else {
                buyAmount.value = validatedAmount
                buyAmount.value?.let { buyValue ->
                    sellAmount.value = (buyValue / conversionRate).roundTwoDeciaml()
                    sellAmount.value?.let {
                        chargeAmount.value = calculateChargeForOffers()
                    }
                }
            }
        } catch (e: Exception) {
            resetInput()
        }

    }

    fun onConvert() {
        // Save Current State
        val availableCurrencyListBackup = availableCurrencies.value.toMutableList()
        // Modify this list
        val availableCurrencyList = availableCurrencies.value.toMutableList()
        //Validation Check
        try {
            //Selling Conversion
            availableCurrencies.value.forEach { currency ->
                if (currency.name == sellingCurrency.value?.name) {
                    val totalNeeded = (sellAmount.value ?: 0.0) + chargeAmount.value
                    if (currency.available < totalNeeded || totalNeeded <= 0) {
                        showSuccessDialog(
                            title = "Input Error", message = "Not enough balance to convert!!"
                        )
                        return
                    }
                    val available = currency.available - totalNeeded
                    currency.available = available
                    currency.totalTimesConverted++
                    currency.totalAmountConverted += sellAmount.value ?: 0.0
                    viewModelScope.launch {
                        currencyUseCases.updateAvailableCurrencyUseCase.invoke(currency)
                    }
                }
            }

            //Buying Conversion
            availableCurrencies.value.firstOrNull {
                it.name == buyingCurrency.value?.currencyName
            }.let { buying ->
                val available = (buying?.available ?: 0.0) + (buyAmount.value ?: 0.0)
                if (buying == null) {
                    // Currency not in available list case
                    buyingCurrency.value?.let {
                        val currency = Currency(
                            name = it.currencyName,
                            available = available,
                            rate = it.currencyRate,
                        )
                        availableCurrencyList.add(currency)
                        viewModelScope.launch {
                            currencyUseCases.insertNewCurrencyUseCase(currency)
                        }
                    }
                } else {
                    // Currency in available list case
                    buying.available = available
                    viewModelScope.launch {
                        currencyUseCases.updateAvailableCurrencyUseCase(buying)
                    }
                }
            }

            // Apply changes
            availableCurrencies.value = availableCurrencyList
            showSuccessDialog(
                title = "Conversion Successful",
                message = "You have converted ${sellAmount.value} ${sellingCurrency.value?.name}" + " to ${buyAmount.value} ${buyingCurrency.value?.currencyName}" + " with charge ${chargeAmount.value} ${sellingCurrency.value?.name}"
            )
        } catch (e: Exception) {
            // Reset Case
            availableCurrencies.value = availableCurrencyListBackup
            viewModelScope.launch {
                availableCurrencies.value.forEach {
                    currencyUseCases.updateAvailableCurrencyUseCase.invoke(it)
                }
            }
            showSuccessDialog(title = "Error", message = e.message.toString())
        }
    }

    private suspend fun preloadBaseCurrency() {
        currencyUseCases.getAvailableCurrencyUseCase.invoke(Constants.BASE_CURRENCY).onEach {
            if (it == null) {
                currencyUseCases.insertNewCurrencyUseCase.invoke(currency = Currency.baseCurrencyPreloaded)
            }
        }.launchIn(viewModelScope)
    }

    private fun getAvailableCurrencies() {
        currencyUseCases.getAvailableCurrenciesUseCase().onEach { it ->
            availableCurrencies.value = it
            if (it.isNotEmpty()) {
                sellingCurrency.value = it.first()
                sellableCurrencies.value = it
                chargeRate = it.first().charge
                loadOrFetchCurrencyRates(sellingCurrency.value?.name ?: Constants.BASE_CURRENCY)
            }
        }.launchIn(viewModelScope)
    }

    fun loadOrFetchCurrencyRates(currencyName: String) {
        _state.value = _state.value.copy(isLoading = false, error = false)
        if (currencyRates.containsKey(currencyName)) {
            updateBuyableCurrencies(currencyName)
        } else {
            getRateUseCase(baseCurrency = currencyName).onEach { resource ->
                when (resource) {
                    is Resource.Error -> _state.value = HomeState(
                        error = true, errorMessage = resource.message ?: "Something went wrong"
                    )
                    is Resource.Loading -> _state.value = HomeState(isLoading = true)
                    is Resource.Success -> {
                        _state.value = HomeState(isLoading = false, error = false)
                        currencyRates[currencyName] = resource.data ?: emptyList()
                        updateBuyableCurrencies(currencyName)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun updateBuyableCurrencies(currencyName: String) {
        currencyRates[currencyName]?.let {
            buyableCurrencies.value = it.filter { rate ->
                rate.currencyName != sellingCurrency.value?.name
            }
            buyingCurrency.value = buyableCurrencies.value.first()
            buyingCurrency.value?.let { buying ->
                conversionRate = buying.currencyRate
            }
            resetInput()
        }
    }
}