package com.paymorecase.data.di

import com.paymorecase.data.service.AndroidAudioPlayer
import com.paymorecase.domain.service.AudioPlayer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ServiceModule {

    @Binds
    internal abstract fun bindAndroidAudioPlayer(
        androidAudioPlayer: AndroidAudioPlayer,
    ): AudioPlayer

}