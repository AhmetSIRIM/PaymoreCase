package com.ahmetsirim.paymorecase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.ahmetsirim.paymorecase.BuildConfig
import com.paymorecase.navigation.PaymoreCaseNavHost
import com.paymorecase.ui.theme.PaymoreCaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("RestrictedApi") // This is required for the 'currentBackStack' API
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            // TODO: Remove the apply scope in the below
            val navController = rememberNavController().apply {

                if (!BuildConfig.DEBUG) return@apply

                currentBackStack
                    .collectAsStateWithLifecycle()
                    .value
                    .map { it.destination.route?.substringAfterLast('.') }
                    .also { infoLog -> Log.i("InfoTag (NavTracker)", infoLog.toString()) }
            }

            PaymoreCaseTheme {
                PaymoreCaseNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
        }
    }

}