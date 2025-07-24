package com.paymorecase.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymorecase.domain.model.common.NFCPaymentCardTypeEnum
import com.paymorecase.ui.component.PaymentButton
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview
import com.paymorecase.ui.R as uiRes

@Composable
internal fun MainScreen(
    onNavigateToNFC: (NFCPaymentCardTypeEnum) -> Unit,
    onNavigateToQR: () -> Unit,
    onNavigateToSales: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PaymentButton(
            text = stringResource(uiRes.string.pay_with_credit_card_with_nfc),
            icon = painterResource(uiRes.drawable.ic_credit_card),
            onClick = { onNavigateToNFC(NFCPaymentCardTypeEnum.CREDIT_CARD) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PaymentButton(
            text = stringResource(uiRes.string.pay_with_loyalty_card_with_nfc),
            icon = painterResource(uiRes.drawable.ic_loyalty),
            onClick = { onNavigateToNFC(NFCPaymentCardTypeEnum.LOYALTY_CARD) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PaymentButton(
            text = stringResource(uiRes.string.pay_with_qr_code),
            icon = painterResource(uiRes.drawable.ic_qr),
            onClick = { onNavigateToQR() }
        )

        Spacer(modifier = Modifier.height(64.dp))

        FilledTonalButton(
            content = { Text(stringResource(uiRes.string.navigate_to_sales)) },
            onClick = { onNavigateToSales() }
        )
    }
}

@ResponsivenessCheckerPreview
@Composable
@Preview
private fun MainScreenPreview() {
    PaymoreCaseTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainScreen(
                onNavigateToNFC = {},
                onNavigateToQR = {},
                onNavigateToSales = {}
            )
        }
    }
}