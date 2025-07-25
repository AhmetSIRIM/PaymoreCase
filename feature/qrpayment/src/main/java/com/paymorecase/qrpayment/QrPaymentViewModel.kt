package com.paymorecase.qrpayment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymorecase.domain.model.SalesRecord
import com.paymorecase.domain.repository.SalesRepository
import com.paymorecase.domain.service.AudioPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class QrPaymentViewModel @Inject constructor(
    private val audioPlayer: AudioPlayer,
    private val salesRepository: SalesRepository,
) : ViewModel() {

    fun playBeepSound() = audioPlayer.playBeep()

    fun onQrCodeScanned(qrData: String) {
        viewModelScope.launch {
            // Announce successful scan
            audioPlayer.announceText("Ürün başarıyla okundu")

            // Save sales record
            saveSalesRecord(qrData)

            // Play success beep
            playBeepSound()
        }
    }

    private suspend fun saveSalesRecord(qrData: String) {
        try {
            // Parse QR data (in real app, this would have proper parsing logic)
            val (productId, productName, price) = parseQrData(qrData)

            val salesRecord = SalesRecord.createQrSalesRecord(
                price = price,
                productId = productId,
                productName = productName,
                qrData = qrData
            )

            val recordId = salesRepository.insertSalesRecord(salesRecord)
            Log.d("InfoTag", "QR Sales record saved with ID: $recordId")

        } catch (e: Exception) {
            Log.e("InfoTag", "Error saving QR sales record: ${e.message}", e)
            // Don't fail the payment flow if saving fails
        }
    }

    private fun parseQrData(qrData: String): Triple<String, String, Double> {
        // Simple QR data parsing for demo purposes
        // In real implementation, this would parse actual QR format

        return try {
            // Try to parse if QR contains JSON-like structure
            if (qrData.contains("productId") && qrData.contains("price")) {
                // Mock parsing - in real app use proper JSON parsing
                val productId = extractValue(qrData, "productId") ?: generateProductId()
                val productName = extractValue(qrData, "productName") ?: "QR ile Ödeme"
                val priceStr = extractValue(qrData, "price")
                val price = priceStr?.toDoubleOrNull() ?: generateRandomPrice()

                Triple(productId, productName, price)
            } else {
                // Fallback for simple QR codes
                Triple(
                    generateProductId(),
                    "QR Kodu: ${qrData.take(20)}${if (qrData.length > 20) "..." else ""}",
                    generateRandomPrice()
                )
            }
        } catch (e: Exception) {
            Log.w("InfoTag", "Error parsing QR data, using defaults: ${e.message}")
            Triple(generateProductId(), "QR ile Ödeme", generateRandomPrice())
        }
    }

    private fun extractValue(data: String, key: String): String? {
        // Simple extraction for demo - in real app use proper JSON parser
        val pattern = "\"$key\"\\s*:\\s*\"([^\"]+)\"".toRegex()
        return pattern.find(data)?.groupValues?.get(1)
    }

    private fun generateRandomPrice(): Double {
        // Generate random price between 5.0 and 200.0 for QR payments
        return (5..200).random().toDouble() + (0..99).random().toDouble() / 100
    }

    private fun generateProductId(): String {
        // Generate random product ID for demo purposes
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..6)
            .map { chars.random() }
            .joinToString("")
            .let { "QR$it" }
    }
}