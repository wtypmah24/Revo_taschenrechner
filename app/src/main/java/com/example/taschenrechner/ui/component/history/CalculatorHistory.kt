package com.example.taschenrechner.ui.component.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorHistory(
    history: List<String>,
    onUseHistory: (String) -> Unit,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.height(120.dp)) {
        items(history) { item ->
            Text(
                text = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onUseHistory(item) }
                    .padding(8.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = onClearHistory,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF916363)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Clear history",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
