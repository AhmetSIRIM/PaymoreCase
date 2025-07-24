package com.paymorecase.data.service

import android.content.Context
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.util.Log
import com.paymorecase.common.di.coroutine.scope.CoroutineScopeQualifier
import com.paymorecase.common.di.coroutine.scope.CoroutineScopeTypeEnum
import com.paymorecase.domain.service.AudioPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import com.paymorecase.ui.R as uiRes

@Singleton
class AndroidAudioPlayer @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context,
    @CoroutineScopeQualifier(CoroutineScopeTypeEnum.APPLICATION)
    private val applicationScope: CoroutineScope,
) : AudioPlayer {

    private var textToSpeech: TextToSpeech? = null
    private var isTtsInitialized = false

    init {
        initializeTextToSpeech()
    }

    private fun initializeTextToSpeech() {
        applicationScope.launch {
            textToSpeech = TextToSpeech(applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result = textToSpeech?.setLanguage(Locale("tr", "TR"))
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Fallback to English if Turkish is not supported
                        textToSpeech?.setLanguage(Locale.ENGLISH)
                        Log.w("AudioPlayer", "Turkish language not supported, using English")
                    }

                    // Configure TTS settings
                    textToSpeech?.setSpeechRate(1.0f)
                    textToSpeech?.setPitch(1.0f)

                    isTtsInitialized = true
                    Log.d("AudioPlayer", "TextToSpeech initialized successfully")
                } else {
                    Log.e("AudioPlayer", "TextToSpeech initialization failed with status: $status")
                    isTtsInitialized = false
                }
            }
        }
    }

    override fun playBeep() {
        try {
            val player = MediaPlayer.create(applicationContext, uiRes.raw.payment_beep)

            if (player != null) {
                player.setOnCompletionListener { mediaPlayer ->
                    try {
                        mediaPlayer.release()
                    } catch (e: Exception) {
                        Log.e("AudioPlayer", "Error releasing MediaPlayer", e)
                    }
                }

                player.setOnErrorListener { mediaPlayer, what, extra ->
                    Log.e("AudioPlayer", "MediaPlayer error: what=$what, extra=$extra")
                    try {
                        mediaPlayer.release()
                    } catch (e: Exception) {
                        Log.e("AudioPlayer", "Error releasing MediaPlayer after error", e)
                    }
                    false
                }

                player.start()
            } else {
                Log.e("AudioPlayer", "Could not create MediaPlayer for payment_beep")
            }
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Error playing beep sound", e)
        }
    }

    override fun announceText(text: String) {
        if (!isTtsInitialized) {
            Log.w("AudioPlayer", "TextToSpeech not initialized, cannot announce: $text")
            return
        }

        try {
            val utteranceId = "tts_${System.currentTimeMillis()}"
            textToSpeech?.speak(
                text,
                TextToSpeech.QUEUE_FLUSH, // Use QUEUE_FLUSH to interrupt previous announcements
                null,
                utteranceId
            )
            Log.d("AudioPlayer", "Announcing text: $text")
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Error announcing text: $text", e)
        }
    }

    fun shutdown() {
        try {
            textToSpeech?.stop()
            textToSpeech?.shutdown()
            textToSpeech = null
            isTtsInitialized = false
            Log.d("AudioPlayer", "AudioPlayer shutdown completed")
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Error during AudioPlayer shutdown", e)
        }
    }

}