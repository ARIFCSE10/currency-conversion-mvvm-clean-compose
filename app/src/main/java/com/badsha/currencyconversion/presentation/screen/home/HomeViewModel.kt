package com.badsha.currencyconversion.presentation.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.badsha.currencyconversion.common.Resource
import com.badsha.currencyconversion.domain.use_case.get_rate.GetRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRateUseCase: GetRateUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state


    init {
        getRate()
    }

    private fun getRate() {
        getRateUseCase().onEach { resource ->
            when (resource) {
                is Resource.Error -> _state.value = HomeState(
                    error = true,
                    errorMessage = resource.message ?: "Something went wrong"
                )
                is Resource.Loading -> _state.value = HomeState(isLoading = true)
                is Resource.Success -> _state.value =
                    HomeState(currencies = resource.data ?: emptyList())
            }
        }.launchIn(viewModelScope)
    }
}