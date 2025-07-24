package com.paymorecase.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paymorecase.qrpayment.QrPaymentContainer
import com.paymorecase.qrpayment.QrPaymentRoute

internal fun NavGraphBuilder.setupQrPaymentUsageFlow(
    navController: NavController,
) {
    composable<QrPaymentRoute> {
        QrPaymentContainer(
            onBackPress = { navController.popBackStack() }
        )
    }
}