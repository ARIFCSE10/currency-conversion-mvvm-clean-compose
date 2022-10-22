package com.badsha.currencyconversion.presentation.screen.home

data class HomeState(
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val showSuccessDialog: Boolean = false,
    val errorMessage: String = ""
)