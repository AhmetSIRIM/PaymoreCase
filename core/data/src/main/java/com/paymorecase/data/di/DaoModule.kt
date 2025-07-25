package com.paymorecase.data.di

import com.paymorecase.data.local.dao.SalesRecordDao
import com.paymorecase.data.local.database.PaymoreCaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideTeacherDao(database: PaymoreCaseDatabase): SalesRecordDao = database.salesRecordDao()

}