package com.paymorecase.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paymorecase.sales.SalesContainer
import com.paymorecase.sales.SalesRoute

internal fun NavGraphBuilder.setupSalesUsageFlow(
    navController: NavController,
) {
    composable<SalesRoute> {
        SalesContainer(
            onBackPress = { navController.popBackStack() },
        )
    }
}