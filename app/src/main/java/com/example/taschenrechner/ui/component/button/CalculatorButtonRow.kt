package com.example.taschenrechner.ui.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorButtonRow(
    buttons: List<String>,
    onInput: (String) -> Unit,
    onEvaluate: () -> Unit,
    lastInput: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (b in buttons) {
            val isOperator = b in listOf("+", "-", "*", "/", "%", "^")
            val lastWasOperator = lastInput in listOf(
                "+", "-", "*", "/", "%", "^", ".", "√", "sin", "cos", "tan", "log", "ln",
                null
            )

            val enabled = when {
                b == "=" -> true
                isOperator && lastWasOperator -> false
                b == "." && (lastInput?.contains(".") == true) -> false
                else -> true
            }

            Button(
                onClick = {
                    if (b == "=") onEvaluate() else onInput(b)
                },
                enabled = enabled,
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when {
                        b == "=" -> Color(0xFFBD3F1B)
                        isOperator -> Color(0xFFC0A26D)
                        else -> Color(0xFFE0E0E0)
                    }
                )
            ) {
                Text(
                    text = b,
                    color = if (enabled)
                        if (b in listOf("+", "-", "*", "/", "=")) Color.White else Color.Black
                    else Color.Gray
                )
            }
        }
    }
}
