package com.paymorecase.main

import androidx.compose.runtime.Composable
import com.paymorecase.domain.model.common.PaymentTypeEnum

@Composable
fun MainContainer(
    onNavigateToNFC: (PaymentTypeEnum) -> Unit,
    onNavigateToQR: (PaymentTypeEnum) -> Unit,
    onNavigateToSales: () -> Unit,
) {

    MainScreen(
        onNavigateToNFC = onNavigateToNFC,
        onNavigateToQR = onNavigateToQR,
        onNavigateToSales = onNavigateToSales,
    )

}