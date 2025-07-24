package com.ahmetsirim.paymorecase.ui

import android.annotation.SuppressLint
import android.nfc.NfcAdapter
import android.nfc.tech.IsoDep
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.ahmetsirim.paymorecase.BuildConfig
import com.github.devnied.emvnfccard.exception.CommunicationException
import com.github.devnied.emvnfccard.parser.EmvTemplate
import com.github.devnied.emvnfccard.parser.IProvider
import com.paymorecase.navigation.PaymoreCaseNavHost
import com.paymorecase.ui.theme.PaymoreCaseTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.time.LocalDate
import java.time.ZoneId

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var mNfcAdapter: NfcAdapter? = null

    @SuppressLint("RestrictedApi") // This is required for the ‘currentBackStack’ API
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        enableEdgeToEdge()
        setContent {

            // TODO: Remove the apply scope in the below
            val navController = rememberNavController().apply {

                if (!BuildConfig.DEBUG) return@apply

                currentBackStack
                    .collectAsStateWithLifecycle()
                    .value
                    .map { it.destination.route?.substringAfterLast('.') }
                    .also { infoLog -> Log.i("InfoTag (NavTracker)", infoLog.toString()) }
            }

            PaymoreCaseTheme {
                PaymoreCaseNavHost(
                    modifier = Modifier.Companion.fillMaxSize(),
                    navController = navController
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mNfcAdapter != null) {
            val options = Bundle()
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)

            mNfcAdapter!!.enableReaderMode(
                this,
                { tag ->
                    val isoDep: IsoDep?
                    try {
                        isoDep = IsoDep.get(tag)
                        if (isoDep != null) {
                            (getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(
                                VibrationEffect.createOneShot(
                                    150,
                                    10
                                )
                            )
                        }
                        isoDep!!.connect()
                        val provider = PcscProvider()
                        provider.setmTagCom(isoDep)
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

//                        Log.d("InfoTag", "card.cardNumber: " + card.cardNumber)
//                        Log.d("InfoTag", "card.iban: " + card.iban)
//                        Log.d("InfoTag", "tag.tag.id: " + tag?.id?.joinToString(":") { String.format("%02X", it) })
//                        Log.d("InfoTag", "IsoDep.get(tag).historicalBytes: " + (IsoDep.get(tag).historicalBytes ?: isoDep.hiLayerResponse))

                        val expireDate = card.expireDate
                        var date = LocalDate.of(1999, 12, 31)
                        if (expireDate != null) {
                            date = expireDate.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }
//                        Log.d("InfoTag", date.toString())
                        try {
                            isoDep.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                NfcAdapter.FLAG_READER_NFC_A or
                        NfcAdapter.FLAG_READER_NFC_B or
                        NfcAdapter.FLAG_READER_NFC_F or
                        NfcAdapter.FLAG_READER_NFC_V or
                        NfcAdapter.FLAG_READER_NFC_BARCODE or
                        NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
                options
            )
        }
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
        result = mTagCom.historicalBytes // for tags using NFC-B
        if (result == null) {
            result = mTagCom.hiLayerResponse // for tags using NFC-B
        }
        return result
    }

    fun setmTagCom(mTagCom: IsoDep) {
        this.mTagCom = mTagCom
    }


}