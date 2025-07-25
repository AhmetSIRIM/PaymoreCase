package com.paymorecase.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.paymorecase.data.local.entity.SalesRecordEntity
import com.paymorecase.domain.model.common.PaymentTypeEnum
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesRecordDao {

    @Query("SELECT * FROM sales_records ORDER BY timestamp DESC")
    fun getAllSalesRecords(): Flow<List<SalesRecordEntity>>

    @Query("SELECT * FROM sales_records WHERE id = :id")
    suspend fun getSalesRecordById(id: Long): SalesRecordEntity?

    @Query("SELECT * FROM sales_records WHERE payment_type = :paymentType ORDER BY timestamp DESC")
    suspend fun getSalesRecordsByPaymentType(paymentType: PaymentTypeEnum): List<SalesRecordEntity>

    @Query("SELECT * FROM sales_records WHERE is_synced = 0")
    suspend fun getUnsyncedSalesRecords(): List<SalesRecordEntity>

    @Query("SELECT * FROM sales_records WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    suspend fun getSalesRecordsByDateRange(
        startDate: String,
        endDate: String
    ): List<SalesRecordEntity>

    @Query("SELECT COUNT(*) FROM sales_records")
    suspend fun getTotalSalesCount(): Int

    @Query("SELECT SUM(price) FROM sales_records")
    suspend fun getTotalSalesAmount(): Double?

    @Query("SELECT SUM(price) FROM sales_records WHERE payment_type = :paymentType")
    suspend fun getTotalSalesAmountByPaymentType(paymentType: PaymentTypeEnum): Double?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertSalesRecord(salesRecord: SalesRecordEntity): Long

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertSalesRecords(salesRecords: List<SalesRecordEntity>)

    @Update
    suspend fun updateSalesRecord(salesRecord: SalesRecordEntity)

    @Query("UPDATE sales_records SET is_synced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)

    @Query("UPDATE sales_records SET is_synced = 1 WHERE id IN (:ids)")
    suspend fun markMultipleAsSynced(ids: List<Long>)

    @Delete
    suspend fun deleteSalesRecord(salesRecord: SalesRecordEntity)

    @Query("DELETE FROM sales_records WHERE id = :id")
    suspend fun deleteSalesRecordById(id: Long)

    @Query("DELETE FROM sales_records")
    suspend fun deleteAllSalesRecords()

    @Query("DELETE FROM sales_records WHERE timestamp < :cutoffDate")
    suspend fun deleteOldSalesRecords(cutoffDate: String)
}