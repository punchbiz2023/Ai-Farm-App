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

data class HoldingsUiState(
    val holdings: List<Holding> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repository: HoldingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HoldingsUiState())
    val uiState: StateFlow<HoldingsUiState> = _uiState

    init {
        loadHoldings()
    }

    fun loadHoldings() {
        viewModelScope.launch {
            _uiState.value = HoldingsUiState(isLoading = true)
            try {
                val holdings = repository.getHoldings()
                _uiState.value = HoldingsUiState(holdings = holdings)
            } catch (e: Exception) {
                _uiState.value = HoldingsUiState(error = e.message)
            }
        }
    }
}