package com.badsha.currencyconversion.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.badsha.currencyconversion.common.Constants
import com.badsha.currencyconversion.common.Resource
import com.badsha.currencyconversion.domain.model.Currency
import com.badsha.currencyconversion.domain.use_case.CurrencyUseCases
import com.badsha.currencyconversion.domain.use_case.calculation.ChargeFreeOnTwoHundredSell
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
    private val chargeFreeOnTwoHundredSell: ChargeFreeOnTwoHundredSell,
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

    fun onSellAmountChange(amount: String) {
        try {
            val validatedAmount = amount.replace("..", ".").toDouble()
            if (validatedAmount == 0.0) {
                resetInput()
            } else {
                sellAmount.value = validatedAmount
                sellAmount.value?.let {
                    buyAmount.value = it * conversionRate
                    chargeAmount.value = calculateCharge(it)
                }
            }
        } catch (e: Exception) {
            resetInput()
        }
    }

    private fun calculateCharge(sellAmount: Double): Double {
        val charge = (sellAmount * chargeRate / 100)
        return chargeFreeOnTwoHundredSell.invoke(charge, sellAmount)
    }

    fun resetInput() {
        sellAmount.value = null
        buyAmount.value = null
        chargeAmount.value = 0.0
    }

    fun onBuyAmountChange(amount: String) {
        try {
            val validatedAmount = amount.replace("..", ".").toDouble()
            if (validatedAmount == 0.0) {
                resetInput()
            } else {
                buyAmount.value = validatedAmount
                buyAmount.value?.let {
                    sellAmount.value = (it / conversionRate)
                    chargeAmount.value = ((sellAmount.value ?: 0.0) * chargeRate / 100)
                }
            }
        } catch (e: Exception) {
            resetInput()
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