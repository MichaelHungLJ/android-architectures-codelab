package com.karlpark.architecturecodelab.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CounterUI(count: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Count: $count",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = onDecrement,
                modifier = Modifier.width(150.dp)
            ) {
                Text("Decrement", color = Color.White)
            }

            Button(
                onClick = onIncrement,
                modifier = Modifier.width(150.dp)
            ) {
                Text("Increment", color = Color.White)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterUIPreview() {
    CounterUI(
        count = 0,
        onIncrement = {},
        onDecrement = {}
    )
}
