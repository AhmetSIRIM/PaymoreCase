package com.paymorecase.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paymorecase.nfcpayment.NfcPaymentContainer
import com.paymorecase.nfcpayment.NfcPaymentRoute

internal fun NavGraphBuilder.setupNfcPaymentUsageFlow(
    navController: NavController,
) {
    composable<NfcPaymentRoute> {
        NfcPaymentContainer(
            onBackPress = { navController.popBackStack() }
        )
    }
}