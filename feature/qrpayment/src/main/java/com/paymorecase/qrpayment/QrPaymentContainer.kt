package com.paymorecase.qrpayment

import androidx.compose.runtime.Composable

@Composable
fun QrPaymentContainer(
    onBackPress: () -> Unit,
) {

    QrPaymentScreen(onBackPress)

}