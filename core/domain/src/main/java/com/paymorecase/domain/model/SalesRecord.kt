package com.paymorecase.domain.model

import com.paymorecase.domain.model.common.PaymentTypeEnum
import java.time.LocalDateTime

data class SalesRecord(
    val id: Long = 0,
    val paymentType: PaymentTypeEnum,
    val timestamp: LocalDateTime,
    val price: Double,
    val productId: String,
    val productName: String,

    // NFC-specific fields
    val cardUid: String? = null,
    val cardNumber: String? = null,
    val cardExpireDate: String? = null,

    // QR-specific fields
    val qrData: String? = null,

    // Additional metadata
    val deviceId: String = "TPOS1234",
    val isSynced: Boolean = false,
){
    companion object {
        fun createNfcSalesRecord(
            paymentType: PaymentTypeEnum,
            price: Double,
            productId: String,
            productName: String,
            cardUid: String?,
            cardNumber: String?,
            cardExpireDate: String?,
        ): SalesRecord {
            return SalesRecord(
                paymentType = paymentType,
                timestamp = LocalDateTime.now(),
                price = price,
                productId = productId,
                productName = productName,
                cardUid = cardUid,
                cardNumber = cardNumber,
                cardExpireDate = cardExpireDate
            )
        }

        fun createQrSalesRecord(
            price: Double,
            productId: String,
            productName: String,
            qrData: String,
        ): SalesRecord {
            return SalesRecord(
                paymentType = PaymentTypeEnum.QR_CODE,
                timestamp = LocalDateTime.now(),
                price = price,
                productId = productId,
                productName = productName,
                qrData = qrData
            )
        }
    }
}