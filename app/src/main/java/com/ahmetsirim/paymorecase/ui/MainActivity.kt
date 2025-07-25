package com.ahmetsirim.paymorecase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.paymorecase.navigation.PaymoreCaseNavHost
import com.paymorecase.ui.theme.PaymoreCaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            PaymoreCaseTheme {
                PaymoreCaseNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
        }
    }

}