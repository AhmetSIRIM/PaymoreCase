package com.paymorecase.data.di

import android.content.Context
import androidx.room.Room
import com.paymorecase.data.local.database.PaymoreCaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun providesPaymoreCaseDatabase(
        @ApplicationContext
        applicationContext: Context,
    ): PaymoreCaseDatabase {
        return Room
            .databaseBuilder(
                context = applicationContext,
                klass = PaymoreCaseDatabase::class.java,
                name = "paymore-case-database"
            )
            .build()
    }

}