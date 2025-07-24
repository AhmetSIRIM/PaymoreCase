package com.paymorecase.qrpayment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview

@Composable
internal fun QrPaymentScreen(
    onBackPress: () -> Unit,
) {
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
                onBackPress = {}
            )
        }
    }
}