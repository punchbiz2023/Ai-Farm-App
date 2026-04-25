package com.Uma.byldwealth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Uma.byldwealth.data.model.Holding
import com.Uma.byldwealth.data.repository.HoldingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HoldingDetailUiState(
    val holding: Holding? = null,
    val allocationWeight: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HoldingDetailViewModel @Inject constructor(
    private val repository: HoldingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HoldingDetailUiState())
    val uiState: StateFlow<HoldingDetailUiState> = _uiState

    fun loadHolding(holdingId: String) {
        viewModelScope.launch {
            _uiState.value = HoldingDetailUiState(isLoading = true)
            try {
                val holdings = repository.getHoldings()
                val holding = holdings.find { it.id == holdingId }
                val totalValue = holdings.sumOf { it.currentPrice * it.quantity }
                val thisValue = (holding?.currentPrice ?: 0.0) * (holding?.quantity ?: 0)
                val weight = if (totalValue > 0) (thisValue / totalValue) * 100 else 0.0
                _uiState.value = HoldingDetailUiState(
                    holding = holding,
                    allocationWeight = weight
                )
            } catch (e: Exception) {
                _uiState.value = HoldingDetailUiState(error = e.message)
            }
        }
    }
}