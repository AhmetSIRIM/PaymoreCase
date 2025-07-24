package com.paymorecase.nfcpayment

import androidx.lifecycle.ViewModel
import com.paymorecase.domain.service.AudioPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class NfcPaymentViewModel @Inject constructor(
    private val audioPlayer: AudioPlayer,
) : ViewModel() {

    fun playBeepSound() = audioPlayer.playBeep()

}