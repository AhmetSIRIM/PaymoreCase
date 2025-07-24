package com.paymorecase.common.di.coroutine.scope

import com.paymorecase.common.di.coroutine.dispatcher.DispatcherQualifier
import com.paymorecase.common.di.coroutine.dispatcher.DispatcherTypeEnum
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @Provides
    @Singleton
    @CoroutineScopeQualifier(CoroutineScopeTypeEnum.APPLICATION)
    fun provideApplicationScope(
        @DispatcherQualifier(DispatcherTypeEnum.DEFAULT)
        defaultDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

}