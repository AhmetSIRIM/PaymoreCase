package com.paymorecase.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paymorecase.main.MainContainer
import com.paymorecase.main.MainRoute

internal fun NavGraphBuilder.setupMainUsageFlow(
    navController: NavController,
) {
    composable<MainRoute> {
        MainContainer()
    }
}