package com.karlpark.architecturecodelab.presentation.mvp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.karlpark.architecturecodelab.di.ServiceLocator
import com.karlpark.architecturecodelab.presentation.CounterUI
import com.karlpark.architecturecodelab.presentation.MVVMCounterUI

@Composable
fun MvpCounterScreen() {
    val presenter: CounterMvpContract.Presenter = remember {
        ServiceLocator.getService(CounterMvpContract.Presenter::class.java)
    }

    val countState = remember { mutableIntStateOf(0) }
    val inputState = remember { mutableStateOf("") }
    val mvpView = remember { // What is this for?
        object : CounterMvpContract.View {
            override fun displayCount(count: Int) {
                countState.intValue = count
            }

            override fun displayInput(input: String) {
                inputState.value = input
            }
        }
    }

    DisposableEffect(Unit) {
        presenter.attach(mvpView)
        onDispose { presenter.detach() }
    }

    Column(Modifier.padding(16.dp)) {
        Text("MVP Architecture", style = MaterialTheme.typography.bodyLarge)
        MVVMCounterUI(
            count = countState.intValue,
            onIncrement = presenter::onIncrementClicked,
            onDecrement = presenter::onDecrementClicked,
            onEnter = presenter::onEnter,
            onValueChange = presenter::onValueChange,
            inputValue = inputState.value,
            onUndo = presenter::onUndo,
        )
    }
}
