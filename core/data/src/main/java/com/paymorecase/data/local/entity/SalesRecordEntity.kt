package com.paymorecase.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.paymorecase.domain.model.common.PaymentTypeEnum
import java.time.LocalDateTime

@Entity(tableName = "sales_records")
data class SalesRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "payment_type")
    val paymentType: PaymentTypeEnum,

    @ColumnInfo(name = "timestamp")
    val timestamp: LocalDateTime,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "product_id")
    val productId: String,

    @ColumnInfo(name = "product_name")
    val productName: String,

    // NFC-specific fields
    @ColumnInfo(name = "card_uid")
    val cardUid: String? = null,

    @ColumnInfo(name = "card_number")
    val cardNumber: String? = null,

    @ColumnInfo(name = "card_expire_date")
    val cardExpireDate: String? = null,

    // QR-specific fields
    @ColumnInfo(name = "qr_data")
    val qrData: String? = null,

    // Additional metadata
    @ColumnInfo(name = "device_id")
    val deviceId: String = "TPOS1234",

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false
)