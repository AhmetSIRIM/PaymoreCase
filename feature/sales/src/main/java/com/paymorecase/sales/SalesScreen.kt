package com.paymorecase.sales

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymorecase.domain.model.SalesRecord
import com.paymorecase.domain.model.common.PaymentTypeEnum
import com.paymorecase.ui.component.PaymentTopBar
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.paymorecase.ui.R as uiRes

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun SalesScreen(
    uiState: SalesUiState,
    onBackPress: () -> Unit,
    onDeleteSalesRecord: (SalesRecord) -> Unit = {},
    onDeleteAllRecords: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            PaymentTopBar(
                title = stringResource(uiRes.string.sales_records),
                navigationIconAction = onBackPress,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SalesStatisticsCard(
                totalCount = uiState.totalCount,
                totalAmount = uiState.totalAmount
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(uiRes.string.loading),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else if (uiState.salesRecords.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(uiRes.string.no_sales_records),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = uiState.salesRecords,
                        key = { it.id }
                    ) { salesRecord ->
                        SalesRecordCard(
                            salesRecord = salesRecord,
                            onDelete = { onDeleteSalesRecord(salesRecord) },
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            TextButton(
                modifier = Modifier.fillMaxWidth(),
                content = { Text(text = stringResource(uiRes.string.delete_all_sales_records)) },
                onClick = onDeleteAllRecords
            )
        }
    }
}

@Composable
private fun SalesStatisticsCard(
    totalCount: Int,
    totalAmount: Double,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = totalCount.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(uiRes.string.total_sales),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "₺${String.format("%.2f", totalAmount)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(uiRes.string.total_amount),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun SalesRecordCard(
    salesRecord: SalesRecord,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Product name and payment type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = salesRecord.productName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )

                    PaymentTypeChip(paymentType = salesRecord.paymentType)
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Product ID and price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ID: ${salesRecord.productId}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "₺${String.format("%.2f", salesRecord.price)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Timestamp
                Text(
                    text = salesRecord.timestamp.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Card info for NFC payments
                if (salesRecord.paymentType != PaymentTypeEnum.QR_CODE && salesRecord.cardNumber != null) {
                    Text(
                        text = "Kart: **** **** **** ${salesRecord.cardNumber?.takeLast(4)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Delete button
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(uiRes.string.delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun PaymentTypeChip(paymentType: PaymentTypeEnum) {
    val (text, color) = when (paymentType) {
        PaymentTypeEnum.CREDIT_CARD -> "Kredi Kartı" to Color(0xFF4CAF50)
        PaymentTypeEnum.LOYALTY_CARD -> "Sadakat Kartı" to Color(0xFF2196F3)
        PaymentTypeEnum.QR_CODE -> "QR Kod" to Color(0xFFFF9800)
    }

    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@ResponsivenessCheckerPreview
@Composable
@Preview
private fun SalesScreenPreview() {
    PaymoreCaseTheme {
        val sampleRecords = listOf(
            SalesRecord(
                id = 1,
                paymentType = PaymentTypeEnum.CREDIT_CARD,
                timestamp = LocalDateTime.now(),
                price = 25.50,
                productId = "PRD001",
                productName = "Türk Kahvesi",
                cardNumber = "1234567890123456"
            ),
            SalesRecord(
                id = 2,
                paymentType = PaymentTypeEnum.QR_CODE,
                timestamp = LocalDateTime.now().minusHours(1),
                price = 15.00,
                productId = "QR123",
                productName = "Çay",
                qrData = "sample qr data"
            )
        )

        SalesScreen(
            uiState = SalesUiState(
                salesRecords = sampleRecords,
                totalAmount = 40.50,
                totalCount = 2
            ),
            onBackPress = {}
        )
    }
}