package com.paymorecase.main

import androidx.compose.runtime.Composable
import com.paymorecase.domain.model.common.NFCPaymentCardTypeEnum

@Composable
fun MainContainer(
    onNavigateToNFC: (NFCPaymentCardTypeEnum) -> Unit,
    onNavigateToQR: () -> Unit,
    onNavigateToSales: () -> Unit,
) {

    MainScreen(
        onNavigateToNFC = onNavigateToNFC,
        onNavigateToQR = onNavigateToQR,
        onNavigateToSales = onNavigateToSales,
    )

}