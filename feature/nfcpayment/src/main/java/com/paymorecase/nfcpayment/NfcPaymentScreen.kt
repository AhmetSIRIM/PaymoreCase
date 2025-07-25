package com.paymorecase.nfcpayment

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
    enteredPrice: String,
    onPriceChanged: (String) -> Unit,
    onPriceConfirmed: () -> Unit,
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

    if (uiState is NfcPaymentUiState.Success) {
        onPaymentCompleteSuccessfully()

        InformationalDialog(
            icon = uiRes.drawable.success_operation,
            title = stringResource(uiRes.string.nfc_payment_successfully_completed),
            description = buildString {
                append("Tutar: ₺$enteredPrice")
                append("\n")
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
                        WaitingForCardContent(
                            paymentType = paymentType,
                            enteredPrice = enteredPrice,
                            onPriceChanged = onPriceChanged,
                            onPriceConfirmed = onPriceConfirmed
                        )
                    }

                    is NfcPaymentUiState.Processing -> ProcessingContent()

                    is NfcPaymentUiState.Success,
                    is NfcPaymentUiState.Error,
                        -> {
                        // These states are handled by dialogs above
                        WaitingForCardContent(
                            paymentType = paymentType,
                            enteredPrice = enteredPrice,
                            onPriceChanged = onPriceChanged,
                            onPriceConfirmed = onPriceConfirmed
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun WaitingForCardContent(
    paymentType: PaymentTypeEnum,
    enteredPrice: String,
    onPriceChanged: (String) -> Unit,
    onPriceConfirmed: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Image(
            painter = painterResource(id = uiRes.drawable.ic_nfc),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        // Price Input Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(uiRes.string.enter_payment_amount),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = enteredPrice,
                    onValueChange = { newValue ->
                        // Only allow numbers and one decimal point
                        if (newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            onPriceChanged(newValue)
                        }
                    },
                    label = { Text(stringResource(uiRes.string.amount)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.width(150.dp)
                )

                Text(
                    text = "₺",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Button(
                onClick = {
                    onPriceConfirmed()
                    focusManager.clearFocus()
                },
                enabled = enteredPrice.isNotBlank() && enteredPrice.toDoubleOrNull() != null && enteredPrice.toDouble() > 0,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(uiRes.string.confirm_amount))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when (paymentType) {
                PaymentTypeEnum.CREDIT_CARD -> stringResource(uiRes.string.hold_credit_card_near_device)
                PaymentTypeEnum.LOYALTY_CARD -> stringResource(uiRes.string.hold_loyalty_card_near_device)
                else -> stringResource(uiRes.string.hold_card_near_device)
            },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                enteredPrice = "25.50",
                onPriceChanged = {},
                onPriceConfirmed = {},
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
                enteredPrice = "",
                onPriceChanged = {},
                onPriceConfirmed = {},
                onBackPress = {},
                onPaymentCompleteSuccessfully = {},
                onResetState = {}
            )
        }
    }
}