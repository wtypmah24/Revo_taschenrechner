package com.example.taschenrechner.ui.component.display

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CalculatorDisplay(
    expression: String,
    result: String?,
    error: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = expression.ifEmpty { "0" },
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        } ?: result?.let {
            Text(text = "=$it", style = MaterialTheme.typography.titleLarge)
        }
    }
}
