package com.paymorecase.nfcpayment

import com.paymorecase.domain.model.common.PaymentTypeEnum
import kotlinx.serialization.Serializable

@Serializable
data class NfcPaymentRoute(val paymentTypeEnum: PaymentTypeEnum)