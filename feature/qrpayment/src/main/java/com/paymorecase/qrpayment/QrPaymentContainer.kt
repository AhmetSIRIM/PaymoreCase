package com.paymorecase.qrpayment

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QrPaymentContainer(
    onBackPress: () -> Unit,
) {

    val viewModel = hiltViewModel<QrPaymentViewModel>()

    QrPaymentScreen(
        onBackPress = onBackPress,
        onQrCodeScanned = viewModel::onQrCodeScanned,
    )

}