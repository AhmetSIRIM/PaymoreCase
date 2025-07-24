package com.paymorecase.common.di.coroutine.scope

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineScopeQualifier(val coroutineScopeTypeEnum: CoroutineScopeTypeEnum)