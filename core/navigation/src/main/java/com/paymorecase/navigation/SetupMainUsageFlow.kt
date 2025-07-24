package com.paymorecase.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paymorecase.main.MainContainer
import com.paymorecase.main.MainRoute
import com.paymorecase.qrpayment.QrPaymentRoute

internal fun NavGraphBuilder.setupMainUsageFlow(
    navController: NavController,
) {
    composable<MainRoute> {
        MainContainer(
            onNavigateToQR = { navController.navigate(QrPaymentRoute(paymentTypeEnum = it)) },
            onNavigateToNFC = { Log.i("InfoTag", "onNavigateToNFC triggered. NFC Type: $it") },
            onNavigateToSales = { Log.i("InfoTag", "onNavigateToSales triggered") },
        )
    }
}