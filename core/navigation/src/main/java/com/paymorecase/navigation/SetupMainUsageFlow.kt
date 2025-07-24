package com.paymorecase.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paymorecase.main.MainContainer
import com.paymorecase.main.MainRoute

internal fun NavGraphBuilder.setupMainUsageFlow(
    navController: NavController,
) {
    composable<MainRoute> {
        MainContainer(
            onNavigateToQR = { Log.i("InfoTag", "onNavigateToQR triggered")  },
            onNavigateToNFC = { Log.i("InfoTag", "onNavigateToNFC triggered. NFC Type: $it")  },
            onNavigateToSales = { Log.i("InfoTag", "onNavigateToSales triggered")  },
        )
    }
}