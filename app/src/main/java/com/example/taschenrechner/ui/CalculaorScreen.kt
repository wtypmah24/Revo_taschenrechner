package com.example.taschenrechner.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.taschenrechner.ui.component.button.CalculatorButtonRow
import com.example.taschenrechner.ui.component.button.ScientificButtons
import com.example.taschenrechner.ui.component.button.SystemButtonRow
import com.example.taschenrechner.ui.component.display.CalculatorDisplay
import com.example.taschenrechner.ui.component.history.CalculatorHistory
import com.example.taschenrechner.viewmodel.CalculatorViewModel
import com.example.taschenrechner.viewmodel.UiState

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = hiltViewModel(),
    voiceInput: String? = null,
    onVoiceInput: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(voiceInput) {
        voiceInput?.let { viewModel.onVoiceInput(it) }
    }

    CalculatorContent(
        state = state,
        onInput = { viewModel.onInput(it) },
        onEvaluate = { viewModel.evaluate() },
        onClear = { viewModel.onClear() },
        onDelete = { viewModel.onDelete() },
        onToggleScientific = { viewModel.toggleScientific() },
        onClearHistory = { viewModel.clearHistory() },
        lastInput = state.expression.lastOrNull()?.toString() ?: "",
        onVoiceInput = onVoiceInput,
        modifier = modifier,
    )
}

@Composable
fun CalculatorContent(
    state: UiState,
    onInput: (String) -> Unit,
    onEvaluate: () -> Unit,
    onClear: () -> Unit,
    onDelete: () -> Unit,
    onToggleScientific: () -> Unit,
    onClearHistory: () -> Unit,
    lastInput: String,
    onVoiceInput: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttons = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "=", "+")
    )
    val sciButtons = listOf("sin", "cos", "tan", "log", "ln", "√", "^", "%", "(", ")")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CalculatorDisplay(
            expression = state.expression,
            result = state.result,
            error = state.error,
            modifier = Modifier.weight(0.2f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(
                    1f,
                    fill = false
                )
        ) {
            buttons.forEach { row ->
                CalculatorButtonRow(
                    buttons = row,
                    onInput = onInput,
                    onEvaluate = onEvaluate,
                    lastInput = lastInput,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (state.scientificMode) {
                Spacer(modifier = Modifier.height(16.dp))
                ScientificButtons(buttons = sciButtons, onInput = onInput, lastInput = lastInput)
            }

            Spacer(modifier = Modifier.height(8.dp))
            SystemButtonRow(
                onClear = onClear,
                onDelete = onDelete,
                onToggleScientific = onToggleScientific,
                onVoiceInput = onVoiceInput
            )
        }

        CalculatorHistory(
            history = state.history,
            onUseHistory = onInput,
            onClearHistory = onClearHistory
        )
    }
}


// ------Preview------
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Calculator UI Preview"
)
@Composable
fun CalculatorPreview() {
    val fakeState = UiState(
        expression = "12+7*3",
        result = "33",
        history = listOf("2+2=4", "5*6=30"),
        error = null,
        scientificMode = true
    )

    MaterialTheme {
        CalculatorContent(
            state = fakeState,
            onInput = {},
            onEvaluate = {},
            onClear = {},
            onDelete = {},
            onToggleScientific = {},
            onClearHistory = {},
            lastInput = "",
            onVoiceInput = {}
        )
    }
}

