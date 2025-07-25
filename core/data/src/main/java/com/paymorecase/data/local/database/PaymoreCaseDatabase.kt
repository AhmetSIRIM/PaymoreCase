package com.paymorecase.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paymorecase.data.local.converter.Converters
import com.paymorecase.data.local.dao.SalesRecordDao
import com.paymorecase.data.local.entity.SalesRecordEntity

@Database(
    entities = [SalesRecordEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    Converters::class
)
internal abstract class PaymoreCaseDatabase : RoomDatabase() {
    abstract fun salesRecordDao(): SalesRecordDao
}