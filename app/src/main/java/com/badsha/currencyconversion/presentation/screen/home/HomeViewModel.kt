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

    val currencyRates: MutableState<List<Currency>> = mutableStateOf(emptyList())
    val availableCurrencies: MutableState<List<Currency>> = mutableStateOf(emptyList())

    lateinit var sellingCurrency: MutableState<Currency>
    lateinit var sellableCurrencies: MutableState<List<Currency>>
    val sellAmount: MutableState<Double?> = mutableStateOf(null)

    lateinit var buyingCurrency: MutableState<Currency>
    lateinit var buyableCurrencies: MutableState<List<Currency>>
    val buyAmount: MutableState<Double?> = mutableStateOf(null)

    val chargeAmount: MutableState<Double> = mutableStateOf(0.0)
    var chargeRate: Double = 0.0
    var conversionRate: Double = 0.0

    init {
        viewModelScope.launch {
            getRate()
            getAvailableCurrencies()
            sellingCurrency = mutableStateOf(availableCurrencies.value.first())
            sellableCurrencies = mutableStateOf(availableCurrencies.value)
            chargeRate = sellingCurrency.value.charge

            buyingCurrency = mutableStateOf(availableCurrencies.value.last())
            buyableCurrencies = mutableStateOf(availableCurrencies.value)
            conversionRate = buyingCurrency.value.rate
        }
    }

    val getConversionSuccessContent: String
        get() {
            return "You have converted ${sellAmount.value} ${sellingCurrency.value.name}" +
                    " to ${buyAmount.value} ${buyingCurrency.value.name}" +
                    " with charge ${chargeAmount.value} ${sellingCurrency.value.name}"
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
                    chargeAmount.value = calculateCharge()
                }
            }
        } catch (e: Exception) {
            resetInput()
        }
    }

    fun showAlertDialog() {
        _state.value = HomeState(isLoading = false, error = false, showAlertDialog = true)
    }

    fun hideAlertDialog() {
        _state.value = HomeState(isLoading = false, error = false, showAlertDialog = false)
    }


    private fun calculateCharge(): Double {
        var baseCharge = ((sellAmount.value ?: 0.0) * chargeRate / 100).roundTwoDeciaml()
        baseCharge = chargeCalculationUseCases.chargeFreeOnTwoHundredSell.invoke(
            baseCharge, sellAmount.value?.roundTwoDeciaml() ?: 0.0
        )

        availableCurrencies.value.firstOrNull {
            it.name == sellingCurrency.value.name
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
                        chargeAmount.value = calculateCharge()
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
            availableCurrencies.value.firstOrNull {
                it.name == sellingCurrency.value.name
            }?.let {
                val totalNeeded = (sellAmount.value ?: 0.0) + chargeAmount.value
                if (it.available < totalNeeded || totalNeeded <= 0) return
                val available = it.available - totalNeeded
                val sellingCurrency = it.copy(available = available)
                availableCurrencyList.remove(it)
                availableCurrencyList.add(sellingCurrency)
            }

            //Buying Conversion
            availableCurrencies.value.firstOrNull {
                it.name == buyingCurrency.value.name
            }?.let {
                val available = it.available + (buyAmount.value ?: 0.0)
                val buyingCurrency = it.copy(available = available)
                availableCurrencyList.remove(it)
                availableCurrencyList.add(buyingCurrency)
            }

            // Apply chnages
            availableCurrencies.value = availableCurrencyList
            showAlertDialog()
        } catch (e: Exception) {
            availableCurrencies.value = availableCurrencyListBackup
        }
    }


    private fun getAvailableCurrencies() {
        availableCurrencies.value = listOf<Currency>(
            Currency(Constants.BASE_CURRENCY, 1.0, 1000.0),
            Currency("USD", 1.1),
        )
    }

    private fun getRate() {
        _state.value = HomeState(isLoading = false, error = false)
        currencyRates.value = listOf<Currency>(
            Currency(Constants.BASE_CURRENCY, 1.0),
            Currency("USD", 1.1),
        )
        return
        getRateUseCase().onEach { resource ->
            when (resource) {
                is Resource.Error -> _state.value = HomeState(
                    error = true, errorMessage = resource.message ?: "Something went wrong"
                )
                is Resource.Loading -> _state.value = HomeState(isLoading = true)
                is Resource.Success -> {
                    _state.value = HomeState(isLoading = false, error = false)
                    currencyRates.value = resource.data ?: emptyList()
                }
            }
        }.launchIn(viewModelScope)
    }
}