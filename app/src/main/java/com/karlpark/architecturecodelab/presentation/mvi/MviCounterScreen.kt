package com.karlpark.architecturecodelab.presentation.mvi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karlpark.architecturecodelab.di.ServiceLocator
import com.karlpark.architecturecodelab.presentation.CounterUI
import com.karlpark.architecturecodelab.presentation.MVVMCounterUI

@Composable
fun MviCounterScreen() {
    val processor: CounterMVIReducer = viewModel(factory = ServiceLocator.getService(ViewModelProvider.Factory::class.java))
    val state by processor.state.collectAsStateWithLifecycle()
    val inputValue by processor.inputValue.collectAsStateWithLifecycle()


    Column(Modifier.padding(16.dp)) {
        Text("MVI Architecture", style = MaterialTheme.typography.bodyLarge)
        MVVMCounterUI(
            count = state.count,
            onIncrement = { processor.processIntent(CounterIntent.Increment) },
            onDecrement = { processor.processIntent(CounterIntent.Decrement) },
            inputValue = inputValue,
            onValueChange = { processor.processIntent(CounterIntent.UpdateCounterInput(it)) },
            onEnter = { processor.processIntent(CounterIntent.onEnter) },
            onUndo = { processor.processIntent(CounterIntent.Undo) }
        )
    }
}
