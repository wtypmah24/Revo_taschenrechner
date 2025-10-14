package com.example.taschenrechner.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taschenrechner.domain.CalculatorEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UiState(
    val expression: String = "",
    val result: String? = null,
    val history: List<String> = emptyList(),
    val error: String? = null,
    val scientificMode: Boolean = false
)

open class CalculatorViewModel(private val engine: CalculatorEngine = CalculatorEngine()) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onInput(token: String) {
        _uiState.value =
            _uiState.value.copy(expression = _uiState.value.expression + token, error = null)
    }

    fun onDelete() {
        val expr = _uiState.value.expression
        if (expr.isNotEmpty()) _uiState.value = _uiState.value.copy(expression = expr.dropLast(1))
    }

    fun onClear() {
        _uiState.value = _uiState.value.copy(
            expression = "",
            result = null,
            error = null
        )
    }

    fun clearHistory() {
        _uiState.value = _uiState.value.copy(
            history = emptyList()
        )
    }


    fun toggleScientific() {
        _uiState.value = _uiState.value.copy(scientificMode = !_uiState.value.scientificMode)
    }

    fun evaluate() {
        val expr = _uiState.value.expression
        viewModelScope.launch {
            val res = engine.evaluate(expr)
            if (res.isSuccess) {
                val value = res.getOrThrow()
                _uiState.value = _uiState.value.copy(
                    expression = "",
                    result = value.toString(),
                    history = listOf("$expr = $value") + _uiState.value.history,
                    error = null
                )
            } else {
                _uiState.value =
                    _uiState.value.copy(error = res.exceptionOrNull()?.message ?: "Error")
            }
        }
    }

    fun useHistoryItem(item: String) {
        val expr = item.substringBefore(" = ").trim()
        _uiState.value = _uiState.value.copy(expression = expr)
    }
}
