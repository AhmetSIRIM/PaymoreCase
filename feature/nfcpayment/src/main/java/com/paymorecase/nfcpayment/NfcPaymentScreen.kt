package com.paymorecase.nfcpayment

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymorecase.domain.model.common.PaymentTypeEnum
import com.paymorecase.ui.component.InformationalDialog
import com.paymorecase.ui.component.PaymentTopBar
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview
import com.paymorecase.ui.R as uiRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NfcPaymentScreen(
    uiState: NfcPaymentUiState,
    paymentType: PaymentTypeEnum,
    onBackPress: () -> Unit,
    onPaymentCompleteSuccessfully: () -> Unit,
    onResetState: () -> Unit,
) {

    val context = LocalContext.current

    val titleText = when (paymentType) {
        PaymentTypeEnum.CREDIT_CARD -> stringResource(uiRes.string.nfc_credit_card_payment)
        PaymentTypeEnum.LOYALTY_CARD -> stringResource(uiRes.string.nfc_loyalty_card_payment)
        else -> stringResource(uiRes.string.nfc_payment)
    }

    // Handle success state with dialog
    if (uiState is NfcPaymentUiState.Success) {
        onPaymentCompleteSuccessfully()

        InformationalDialog(
            icon = uiRes.drawable.success_operation,
            title = stringResource(uiRes.string.nfc_payment_successfully_completed),
            description = buildString {
                append(
                    stringResource(
                        uiRes.string.card_number_info,
                        uiState.cardInfo.cardNumber ?: "N/A"
                    )
                )
                append("\n")
                append(
                    stringResource(
                        uiRes.string.expire_date_info,
                        uiState.cardInfo.expireDate.toString()
                    )
                )
                append("\n")
                append(stringResource(uiRes.string.card_id_info, uiState.cardInfo.tagId))
            },
            buttonTextAndActionPair = stringResource(uiRes.string.return_to_main_screen) to {
                onResetState()
                onBackPress()
            }
        )
    }

    // Handle error state with dialog
    if (uiState is NfcPaymentUiState.Error) {
        Toast.makeText(context, uiRes.string.something_went_wrong, Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            PaymentTopBar(
                title = titleText,
                navigationIconAction = onBackPress,
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is NfcPaymentUiState.WaitingForCard -> {
                        WaitingForCardContent(paymentType = paymentType)
                    }

                    is NfcPaymentUiState.Processing -> ProcessingContent()

                    is NfcPaymentUiState.Success,
                    is NfcPaymentUiState.Error,
                        -> {
                        // These states are handled by dialogs above
                        // Show empty content or keep the last valid state
                        WaitingForCardContent(paymentType = paymentType)
                    }
                }
            }
        }
    )
}

@Composable
private fun WaitingForCardContent(paymentType: PaymentTypeEnum) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painter = painterResource(id = uiRes.drawable.ic_nfc),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Text(
            text = when (paymentType) {
                PaymentTypeEnum.CREDIT_CARD -> stringResource(uiRes.string.hold_credit_card_near_device)
                PaymentTypeEnum.LOYALTY_CARD -> stringResource(uiRes.string.hold_loyalty_card_near_device)
                else -> stringResource(uiRes.string.hold_card_near_device)
            },
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
private fun ProcessingContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(60.dp),
            strokeWidth = 4.dp
        )

        Text(
            text = stringResource(uiRes.string.processing_card),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@ResponsivenessCheckerPreview
@Composable
@Preview
private fun NfcPaymentScreenWaitingPreview() {
    PaymoreCaseTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NfcPaymentScreen(
                uiState = NfcPaymentUiState.WaitingForCard,
                paymentType = PaymentTypeEnum.CREDIT_CARD,
                onBackPress = {},
                onPaymentCompleteSuccessfully = {},
                onResetState = {}
            )
        }
    }
}

@ResponsivenessCheckerPreview
@Composable
@Preview
private fun NfcPaymentScreenProcessingPreview() {
    PaymoreCaseTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NfcPaymentScreen(
                uiState = NfcPaymentUiState.Processing,
                paymentType = PaymentTypeEnum.LOYALTY_CARD,
                onBackPress = {},
                onPaymentCompleteSuccessfully = {},
                onResetState = {}
            )
        }
    }
}