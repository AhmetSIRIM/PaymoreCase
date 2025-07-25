package com.paymorecase.sales

import com.paymorecase.domain.model.SalesRecord

data class SalesUiState(
    val salesRecords: List<SalesRecord> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val totalAmount: Double = 0.0,
    val totalCount: Int = 0,
)