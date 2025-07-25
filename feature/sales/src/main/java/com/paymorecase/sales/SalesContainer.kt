package com.paymorecase.sales

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SalesContainer(
    onBackPress: () -> Unit,
) {
    val viewModel = hiltViewModel<SalesViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SalesScreen(
        uiState = uiState,
        onBackPress = onBackPress,
        onDeleteSalesRecord = viewModel::deleteSalesRecord,
        onDeleteAllRecords = viewModel::deleteAllSalesRecords
    )
}