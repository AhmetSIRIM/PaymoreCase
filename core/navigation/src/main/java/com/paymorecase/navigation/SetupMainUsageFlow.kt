package com.paymorecase.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
    composable<MainRoute>(
        enterTransition = {
            fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300)) + slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300)) + slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        }
    ) {
        MainContainer(
            onNavigateToQR = { navController.navigate(QrPaymentRoute(paymentTypeEnum = it)) },
            onNavigateToNFC = { navController.navigate(NfcPaymentRoute(paymentTypeEnum = it)) },
            onNavigateToSales = { navController.navigate(SalesRoute) },
        )
    }
}