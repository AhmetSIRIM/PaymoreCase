package com.paymorecase.nfcpayment

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NfcPaymentContainer(
    onBackPress: () -> Unit,
) {

    val viewModel = hiltViewModel<NfcPaymentViewModel>()

    NfcPaymentScreen(
        onBackPress = onBackPress,
        onPaymentCompleteSuccessfully = viewModel::playBeepSound,
    )

}