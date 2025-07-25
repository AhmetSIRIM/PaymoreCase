package com.paymorecase.data.di

import com.paymorecase.data.repository.SalesRepositoryImpl
import com.paymorecase.domain.repository.SalesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindStudentRepository(
        salesRepositoryImpl: SalesRepositoryImpl,
    ): SalesRepository

}