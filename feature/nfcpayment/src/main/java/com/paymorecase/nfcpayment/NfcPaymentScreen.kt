package com.paymorecase.nfcpayment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymorecase.ui.component.PaymentTopBar
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NfcPaymentScreen(
    onBackPress: () -> Unit,
    onPaymentCompleteSuccessfully: () -> Unit,
) {
    Scaffold(
        topBar = {
            PaymentTopBar(
                title = "NFC ile ödeme",
                navigationIconAction = onBackPress,
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {

            }
        }
    )
}

@ResponsivenessCheckerPreview
@Composable
@Preview
private fun NfcPaymentScreenPreview() {
    PaymoreCaseTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NfcPaymentScreen(
                onBackPress = {},
                onPaymentCompleteSuccessfully = {},
            )
        }
    }
}