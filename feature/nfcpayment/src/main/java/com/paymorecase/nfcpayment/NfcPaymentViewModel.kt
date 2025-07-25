package com.paymorecase.nfcpayment

import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.github.devnied.emvnfccard.exception.CommunicationException
import com.github.devnied.emvnfccard.parser.EmvTemplate
import com.github.devnied.emvnfccard.parser.IProvider
import com.paymorecase.domain.model.common.PaymentTypeEnum
import com.paymorecase.domain.service.AudioPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

data class NfcCardInfo(
    val cardNumber: String?,
    val iban: String?,
    val tagId: String,
    val historicalBytes: String,
    val expireDate: LocalDate,
    val paymentType: PaymentTypeEnum,
)

sealed class NfcPaymentUiState {
    data object WaitingForCard : NfcPaymentUiState()
    data object Processing : NfcPaymentUiState()
    data class Success(val cardInfo: NfcCardInfo) : NfcPaymentUiState()
    data class Error(val message: String) : NfcPaymentUiState()
}

@HiltViewModel
internal class NfcPaymentViewModel @Inject constructor(
    private val audioPlayer: AudioPlayer,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val paymentType = savedStateHandle.toRoute<NfcPaymentRoute>().paymentTypeEnum

    private val _uiState = MutableStateFlow<NfcPaymentUiState>(NfcPaymentUiState.WaitingForCard)
    val uiState: StateFlow<NfcPaymentUiState> = _uiState.asStateFlow()

    fun playBeepSound() = audioPlayer.playBeep()

    fun processNfcTag(tag: Tag) {
        viewModelScope.launch {
            _uiState.update { NfcPaymentUiState.Processing }

            delay(2000) // Simulate loading

            try {
                val isoDep = IsoDep.get(tag)
                if (isoDep != null) {
                    when (paymentType) {
                        PaymentTypeEnum.CREDIT_CARD -> audioPlayer.announceText("Kart kabul edildi")
                        PaymentTypeEnum.LOYALTY_CARD -> audioPlayer.announceText("Sadakat kartı kabul edildi")
                        else -> audioPlayer.announceText("Kart kabul edildi")
                    }
                }

                isoDep?.connect()
                val provider = PcscProvider()
                provider.setmTagCom(isoDep!!)

                val config = EmvTemplate.Config()
                    .setContactLess(true)
                    .setReadAllAids(true)
                    .setReadTransactions(true)
                    .setRemoveDefaultParsers(false)
                    .setReadAt(true)

                val parser = EmvTemplate.Builder()
                    .setProvider(provider)
                    .setConfig(config)
                    .build()

                val card = parser.readEmvCard()

                val tagId = tag.id?.joinToString(":") { String.format("%02X", it) } ?: ""
                val historicalBytes = IsoDep.get(tag)?.historicalBytes ?: isoDep.hiLayerResponse
                val historicalBytesStr =
                    historicalBytes?.joinToString(":") { String.format("%02X", it) } ?: ""

                val expireDate = card.expireDate?.toInstant()?.atZone(ZoneId.systemDefault())
                    ?.toLocalDate()
                    ?: LocalDate.of(1999, 12, 31)

                val cardInfo = NfcCardInfo(
                    cardNumber = card.cardNumber,
                    iban = card.iban,
                    tagId = tagId,
                    historicalBytes = historicalBytesStr,
                    expireDate = expireDate,
                    paymentType = paymentType
                ).also {
                    Log.d("InfoTag", it.toString())
                }

                try {
                    isoDep.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                _uiState.update { NfcPaymentUiState.Success(cardInfo) }
                audioPlayer.announceText("Satın alma tamamlandı")
                playBeepSound()

            } catch (e: IOException) {
                Log.e("InfoTag", "NFC IOException: ${e.message}", e)
                _uiState.update { NfcPaymentUiState.Error("Kart okuma hatası: ${e.message}") }
            } catch (e: Exception) {
                Log.e("InfoTag", "NFC Exception: ${e.message}", e)
                _uiState.update { NfcPaymentUiState.Error("Beklenmeyen hata: ${e.message}") }
            }
        }
    }

    fun resetState() {
        _uiState.update { NfcPaymentUiState.WaitingForCard }
    }

    override fun onCleared() {
        audioPlayer.shutdown()
        super.onCleared()
    }

}

class PcscProvider : IProvider {

    private lateinit var mTagCom: IsoDep

    @Throws(CommunicationException::class)
    override fun transceive(pCommand: ByteArray?): ByteArray? {
        var response: ByteArray? = null
        try {
            // send command to emv card
            if (mTagCom.isConnected) {
                response = mTagCom.transceive(pCommand)
            }
        } catch (e: IOException) {
            throw CommunicationException(e.message)
        }
        return response
    }

    override fun getAt(): ByteArray {
        var result: ByteArray?
        result = mTagCom.historicalBytes // for tags using NFC-A
        if (result == null) {
            result = mTagCom.hiLayerResponse // for tags using NFC-B
        }
        return result
    }

    fun setmTagCom(mTagCom: IsoDep) {
        this.mTagCom = mTagCom
    }
}