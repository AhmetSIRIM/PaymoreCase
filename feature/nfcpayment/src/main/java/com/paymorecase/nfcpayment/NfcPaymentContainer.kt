package com.paymorecase.nfcpayment

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NfcPaymentContainer(
    onBackPress: () -> Unit,
) {
    val context = LocalContext.current

    val viewModel = hiltViewModel<NfcPaymentViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val paymentType = viewModel.paymentType

    DisposableEffect(context) {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)

        val nfcReaderCallback = NfcAdapter.ReaderCallback { tag: Tag ->
            val isoDep = IsoDep.get(tag)
            if (isoDep != null) {
                viewModel.processNfcTag(tag)
            }
        }

        if (nfcAdapter != null) {
            val options = Bundle().apply {
                putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)
            }

            nfcAdapter.enableReaderMode(
                context as androidx.activity.ComponentActivity,
                nfcReaderCallback,
                NfcAdapter.FLAG_READER_NFC_A or
                        NfcAdapter.FLAG_READER_NFC_B or
                        NfcAdapter.FLAG_READER_NFC_F or
                        NfcAdapter.FLAG_READER_NFC_V or
                        NfcAdapter.FLAG_READER_NFC_BARCODE or
                        NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
                options
            )
        }

        onDispose {
            nfcAdapter?.disableReaderMode(context as androidx.activity.ComponentActivity)
        }
    }

    NfcPaymentScreen(
        uiState = uiState,
        paymentType = paymentType,
        onBackPress = onBackPress,
        onPaymentCompleteSuccessfully = viewModel::playBeepSound,
        onResetState = viewModel::resetState
    )
}