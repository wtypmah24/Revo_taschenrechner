package com.example.taschenrechner.ui.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScientificButtons(
    buttons: List<String>,
    onInput: (String) -> Unit,
    lastInput: String?,
    modifier: Modifier = Modifier
) {
    val rows = buttons.chunked(5)
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (b in row) {
                    val isOperator = b in listOf("sin", "cos", "tan", "log", "ln", "√", "^", "%")
                    val lastWasOperator = when {
                        lastInput.isNullOrEmpty() -> false
                        lastInput.last().isLetter() -> true
                        lastInput.last() in listOf('+', '-', '*', '/', '%', '^', '.', '√') -> true
                        else -> false
                    }
                    val enabled = when {
                        b == "=" -> true
                        isOperator && lastWasOperator -> false
                        b == "." && (lastInput?.contains(".") == true) -> false
                        else -> true
                    }
                    Button(
                        onClick = { onInput(b) },
                        enabled = enabled,
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 40.dp, max = 50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF91C2D7),
                            contentColor = Color.Black
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = b,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
