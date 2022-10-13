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
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    val currencyRates: MutableState<List<Currency>> = mutableStateOf(emptyList())
    val availableCurrencies: MutableState<List<Currency>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            getRate()
            getAvailableCurrencies()
        }
    }

    private fun getAvailableCurrencies() {
        availableCurrencies.value = listOf<Currency>(
            Currency(Constants.BASE_CURRENCY, 1.0, 1000.0),
            Currency("USD", 1.1),
        )
    }

    private fun getRate() {
        _state.value =
            HomeState(isLoading = false, error = false)
        currencyRates.value = listOf<Currency>(
            Currency(Constants.BASE_CURRENCY, 1.0),
            Currency("USD", 1.1),
        )
        return
        getRateUseCase().onEach { resource ->
            when (resource) {
                is Resource.Error -> _state.value = HomeState(
                    error = true,
                    errorMessage = resource.message ?: "Something went wrong"
                )
                is Resource.Loading -> _state.value = HomeState(isLoading = true)
                is Resource.Success -> {
                    _state.value =
                        HomeState(isLoading = false, error = false)
                    currencyRates.value = resource.data ?: emptyList()
                }
            }
        }.launchIn(viewModelScope)
    }
}