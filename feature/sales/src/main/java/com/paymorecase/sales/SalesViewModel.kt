package com.paymorecase.sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paymorecase.domain.model.SalesRecord
import com.paymorecase.domain.repository.SalesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SalesViewModel @Inject constructor(
    private val salesRepository: SalesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SalesUiState(isLoading = true))
    val uiState: StateFlow<SalesUiState> = _uiState.asStateFlow()

    init {
        loadSalesRecords()
    }

    private fun loadSalesRecords() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            delay(200) // Simulate loading

            try {
                salesRepository.getAllSalesRecords().collect { records ->
                    val totalAmount = async { salesRepository.getTotalSalesAmount() }
                    val totalCount = async { salesRepository.getTotalSalesCount() }

                    _uiState.update {
                        SalesUiState(
                            salesRecords = records,
                            isLoading = false,
                            totalAmount = totalAmount.await(),
                            totalCount = totalCount.await()
                        )
                    }
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Something went wrong"
                    )
                }
            }
        }
    }

    fun deleteSalesRecord(salesRecord: SalesRecord) {
        viewModelScope.launch {
            try {
                salesRepository.deleteSalesRecord(salesRecord)
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Something went wrong"
                    )
                }
            }
        }
    }

    fun deleteAllSalesRecords() {
        viewModelScope.launch {
            try {
                salesRepository.deleteAllSalesRecords()
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Something went wrong"
                    )
                }
            }
        }
    }
}