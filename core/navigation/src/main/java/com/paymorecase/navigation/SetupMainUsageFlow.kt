package com.paymorecase.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paymorecase.main.MainContainer
import com.paymorecase.main.MainRoute
import com.paymorecase.nfcpayment.NfcPaymentRoute
import com.paymorecase.qrpayment.QrPaymentRoute
import com.paymorecase.sales.SalesRoute

internal fun NavGraphBuilder.setupMainUsageFlow(
    navController: NavController,
) {
    composable<MainRoute> {
        MainContainer(
            onNavigateToQR = { navController.navigate(QrPaymentRoute(paymentTypeEnum = it)) },
            onNavigateToNFC = { navController.navigate(NfcPaymentRoute(paymentTypeEnum = it)) },
            onNavigateToSales = { navController.navigate(SalesRoute) },
        )
    }
}