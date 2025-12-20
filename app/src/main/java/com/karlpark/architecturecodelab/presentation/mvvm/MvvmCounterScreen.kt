package com.karlpark.architecturecodelab.presentation.mvvm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karlpark.architecturecodelab.di.ServiceLocator
import com.karlpark.architecturecodelab.presentation.CounterUI
import com.karlpark.architecturecodelab.presentation.MVVMCounterUI

@Composable
fun MvvmCounterScreen() {
    val viewModel: CounterMVVMViewModel = viewModel(factory = ServiceLocator.getService(ViewModelProvider.Factory::class.java))
    val state by viewModel.state
    val inputValue by viewModel.inputValue

    Column(modifier = Modifier.padding(16.dp)) {
        Text("MVVM Architecture", style = MaterialTheme.typography.bodyLarge)
        MVVMCounterUI(
            count = state.count,
            onIncrement = viewModel::increment,
            onDecrement = viewModel::decrement,
            inputValue = inputValue,
            onValueChange = viewModel::onValueChange,
            onEnter = viewModel::onEnter,
            onUndo = viewModel::undoCount,
        )
    }
}
