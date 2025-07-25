package com.paymorecase.data.local.converter

import androidx.room.TypeConverter
import com.paymorecase.domain.model.common.PaymentTypeEnum
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    companion object {
        private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(dateTimeFormatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let {
            LocalDateTime.parse(it, dateTimeFormatter)
        }
    }

    @TypeConverter
    fun fromPaymentTypeEnum(paymentType: PaymentTypeEnum): String {
        return paymentType.name
    }

    @TypeConverter
    fun toPaymentTypeEnum(paymentTypeString: String): PaymentTypeEnum {
        return PaymentTypeEnum.valueOf(paymentTypeString)
    }
}