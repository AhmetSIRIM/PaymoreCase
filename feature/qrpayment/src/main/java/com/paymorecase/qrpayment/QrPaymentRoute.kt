package com.paymorecase.qrpayment

import com.paymorecase.domain.model.common.PaymentTypeEnum
import kotlinx.serialization.Serializable

@Serializable
data class QrPaymentRoute(val paymentTypeEnum: PaymentTypeEnum)