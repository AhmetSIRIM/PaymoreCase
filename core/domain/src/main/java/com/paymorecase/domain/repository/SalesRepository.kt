package com.paymorecase.domain.repository

import com.paymorecase.domain.model.SalesRecord
import com.paymorecase.domain.model.common.PaymentTypeEnum
import kotlinx.coroutines.flow.Flow

interface SalesRepository {

    // Basic CRUD operations
    suspend fun insertSalesRecord(salesRecord: SalesRecord): Long
    suspend fun insertSalesRecords(salesRecords: List<SalesRecord>)
    suspend fun deleteSalesRecord(salesRecord: SalesRecord)
    suspend fun deleteSalesRecordById(id: Long)
    suspend fun deleteAllSalesRecords()
    suspend fun updateSalesRecord(salesRecord: SalesRecord)

    // Query operations
    fun getAllSalesRecords(): Flow<List<SalesRecord>>
    suspend fun getSalesRecordById(id: Long): SalesRecord?
    suspend fun getSalesRecordsByPaymentType(paymentType: PaymentTypeEnum): List<SalesRecord>
    suspend fun getSalesRecordsByDateRange(startDate: String, endDate: String): List<SalesRecord>
    suspend fun getUnsyncedSalesRecords(): List<SalesRecord>

    // Statistics
    suspend fun getTotalSalesCount(): Int
    suspend fun getTotalSalesAmount(): Double
    suspend fun getTotalSalesAmountByPaymentType(paymentType: PaymentTypeEnum): Double

    // Sync operations
    suspend fun markAsSynced(id: Long)
    suspend fun markMultipleAsSynced(ids: List<Long>)

    // Cleanup operations
    suspend fun deleteOldSalesRecords(cutoffDate: String)

}