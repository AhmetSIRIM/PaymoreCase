package com.paymorecase.data.repository

import com.paymorecase.data.local.dao.SalesRecordDao
import com.paymorecase.data.local.entity.SalesRecordEntity
import com.paymorecase.domain.model.SalesRecord
import com.paymorecase.domain.model.common.PaymentTypeEnum
import com.paymorecase.domain.repository.SalesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SalesRepositoryImpl @Inject constructor(
    private val salesRecordDao: SalesRecordDao,
) : SalesRepository {

    override suspend fun insertSalesRecord(salesRecord: SalesRecord): Long {
        return salesRecordDao.insertSalesRecord(salesRecord.toEntity())
    }

    override suspend fun insertSalesRecords(salesRecords: List<SalesRecord>) {
        salesRecordDao.insertSalesRecords(salesRecords.map { it.toEntity() })
    }

    override suspend fun deleteSalesRecord(salesRecord: SalesRecord) {
        salesRecordDao.deleteSalesRecord(salesRecord.toEntity())
    }

    override suspend fun deleteSalesRecordById(id: Long) {
        salesRecordDao.deleteSalesRecordById(id)
    }

    override suspend fun deleteAllSalesRecords() {
        salesRecordDao.deleteAllSalesRecords()
    }

    override suspend fun updateSalesRecord(salesRecord: SalesRecord) {
        salesRecordDao.updateSalesRecord(salesRecord.toEntity())
    }

    override fun getAllSalesRecords(): Flow<List<SalesRecord>> {
        return salesRecordDao.getAllSalesRecords().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getSalesRecordById(id: Long): SalesRecord? {
        return salesRecordDao.getSalesRecordById(id)?.toDomainModel()
    }

    override suspend fun getSalesRecordsByPaymentType(paymentType: PaymentTypeEnum): List<SalesRecord> {
        return salesRecordDao.getSalesRecordsByPaymentType(paymentType)
            .map { it.toDomainModel() }
    }

    override suspend fun getSalesRecordsByDateRange(
        startDate: String,
        endDate: String,
    ): List<SalesRecord> {
        return salesRecordDao.getSalesRecordsByDateRange(startDate, endDate)
            .map { it.toDomainModel() }
    }

    override suspend fun getUnsyncedSalesRecords(): List<SalesRecord> {
        return salesRecordDao.getUnsyncedSalesRecords()
            .map { it.toDomainModel() }
    }

    override suspend fun getTotalSalesCount(): Int {
        return salesRecordDao.getTotalSalesCount()
    }

    override suspend fun getTotalSalesAmount(): Double {
        return salesRecordDao.getTotalSalesAmount() ?: 0.0
    }

    override suspend fun getTotalSalesAmountByPaymentType(paymentType: PaymentTypeEnum): Double {
        return salesRecordDao.getTotalSalesAmountByPaymentType(paymentType) ?: 0.0
    }

    override suspend fun markAsSynced(id: Long) {
        salesRecordDao.markAsSynced(id)
    }

    override suspend fun markMultipleAsSynced(ids: List<Long>) {
        salesRecordDao.markMultipleAsSynced(ids)
    }

    override suspend fun deleteOldSalesRecords(cutoffDate: String) {
        salesRecordDao.deleteOldSalesRecords(cutoffDate)
    }
}

// Extension functions for mapping between domain and data layers
private fun SalesRecord.toEntity(): SalesRecordEntity {
    return SalesRecordEntity(
        id = this.id,
        paymentType = this.paymentType,
        timestamp = this.timestamp,
        price = this.price,
        productId = this.productId,
        productName = this.productName,
        cardUid = this.cardUid,
        cardNumber = this.cardNumber,
        cardExpireDate = this.cardExpireDate,
        qrData = this.qrData,
        deviceId = this.deviceId,
        isSynced = this.isSynced
    )
}

private fun SalesRecordEntity.toDomainModel(): SalesRecord {
    return SalesRecord(
        id = this.id,
        paymentType = this.paymentType,
        timestamp = this.timestamp,
        price = this.price,
        productId = this.productId,
        productName = this.productName,
        cardUid = this.cardUid,
        cardNumber = this.cardNumber,
        cardExpireDate = this.cardExpireDate,
        qrData = this.qrData,
        deviceId = this.deviceId,
        isSynced = this.isSynced
    )
}