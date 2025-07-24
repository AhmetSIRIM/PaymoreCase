package com.paymorecase.main

import com.paymorecase.domain.model.ErrorState

internal class MainContract {

    data class UiState(
        val isSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val errorState: ErrorState? = null,
    )

}