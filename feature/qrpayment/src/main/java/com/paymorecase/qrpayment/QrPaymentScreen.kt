package com.paymorecase.qrpayment

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.paymorecase.ui.component.InformationalDialog
import com.paymorecase.ui.component.PaymentTopBar
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview
import kotlinx.coroutines.delay
import com.paymorecase.ui.R as uiRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun QrPaymentScreen(
    onBackPress: () -> Unit,
    onQrCodeScanned: (String) -> Unit,
) {
    val codeScanner = remember { mutableStateOf<CodeScanner?>(null) }

    var scannedText: String? by rememberSaveable { mutableStateOf(null) }
    var remainingTime: Int by rememberSaveable { mutableIntStateOf(30) }

    scannedText?.let { scannedText: String ->
        // Call the ViewModel function when QR is scanned
        LaunchedEffect(scannedText) {
            onQrCodeScanned(scannedText)
        }

        InformationalDialog(
            icon = uiRes.drawable.success_operation,
            title = stringResource(uiRes.string.qr_payment_successfully_completed),
            description = stringResource(uiRes.string.payment_info, scannedText),
            buttonTextAndActionPair = stringResource(uiRes.string.return_to_main_screen) to onBackPress
        )
    }

    LaunchedEffect(remainingTime) {
        if (scannedText != null) return@LaunchedEffect

        delay(1000)

        if (remainingTime == 0) onBackPress() else remainingTime = remainingTime - 1
    }

    Scaffold(
        topBar = {
            PaymentTopBar(
                title = stringResource(
                    uiRes.string.remaining_time_for_qr_payment,
                    remainingTime
                ),
                navigationIconAction = onBackPress,
            )
        },
        content = {
            Box(
                modifier = Modifier.padding(it),
            ) {
                AndroidView(
                    factory = { context: Context ->
                        val scannerView = CodeScannerView(context).apply {
                            isAutoFocusButtonVisible = true
                            isFlashButtonVisible = true
                        }

                        codeScanner.value = CodeScanner(context, scannerView).apply {
                            scanMode = ScanMode.SINGLE
                            isAutoFocusEnabled = true
                            decodeCallback = DecodeCallback { result -> scannedText = result.text }
                            errorCallback = ErrorCallback { throwable ->
                                if (BuildConfig.DEBUG) {
                                    Log.e(
                                        "Error Tag",
                                        "Code scanner error: ${throwable.message}",
                                        throwable
                                    )
                                }

                                Toast.makeText(
                                    context,
                                    throwable.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        scannerView
                    },
                )

                LaunchedEffect(Unit) {
                    codeScanner.value?.startPreview()
                }

                DisposableEffect(Unit) {
                    onDispose {
                        codeScanner.value?.releaseResources()
                    }
                }
            }
        }
    )
}

@ResponsivenessCheckerPreview
@Composable
@Preview
private fun QrPaymentScreenPreview() {
    PaymoreCaseTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QrPaymentScreen(
                onBackPress = {},
                onQrCodeScanned = {},
            )
        }
    }
}