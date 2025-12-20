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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MVVMCounterUI(
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    inputValue: String,
    onValueChange: (String) -> Unit,
    onEnter: (String) -> Unit,
    onUndo: () -> Unit,
) {
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Button(
                onClick = { onUndo() },
                modifier = Modifier.width(100.dp)
            ) {
                Text("Undo")
            }

            TextField(
                value = inputValue,
                onValueChange = { onValueChange(it) },
                singleLine = true,
                modifier = Modifier.width(140.dp)
            )

            Button(
                onClick = { onEnter(inputValue) },
                modifier = Modifier.width(100.dp)
            ) {
                Text("Enter")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MVVMCounterUIPreview() {
    MVVMCounterUI(
        count = 0,
        onIncrement = {},
        onDecrement = {},
        inputValue = "",
        onValueChange = {},
        onEnter = {},
        onUndo = {},
    )
}
