package com.paymorecase.data.service

import android.content.Context
import android.media.MediaPlayer
import com.paymorecase.domain.service.AudioPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import com.paymorecase.ui.R as uiRes

@Singleton
class AndroidAudioPlayer @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context,
) : AudioPlayer {

    override fun playBeep() {
        val player = MediaPlayer.create(applicationContext, uiRes.raw.payment_beep)
        player.setOnCompletionListener { it.release() }
        player.setOnErrorListener { mp, _, _ ->
            mp.release()
            false
        }
        player.start()
    }

}