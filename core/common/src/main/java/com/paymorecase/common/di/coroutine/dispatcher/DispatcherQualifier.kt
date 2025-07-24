package com.paymorecase.common.di.coroutine.dispatcher

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherQualifier(val dispatcherTypeEnum: DispatcherTypeEnum)