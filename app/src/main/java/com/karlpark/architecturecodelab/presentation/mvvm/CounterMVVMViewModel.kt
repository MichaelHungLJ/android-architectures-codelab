package com.karlpark.architecturecodelab.presentation.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.karlpark.architecturecodelab.domain.CounterState
import com.karlpark.architecturecodelab.domain.DecrementCounterUseCase
import com.karlpark.architecturecodelab.domain.GetCounterUseCase
import com.karlpark.architecturecodelab.domain.IncrementCounterUseCase
import com.karlpark.architecturecodelab.presentation.Screen

class CounterMVVMViewModel(
    private val incrementUseCase: IncrementCounterUseCase,
    private val decrementUseCase: DecrementCounterUseCase,
    getCounterUseCase: GetCounterUseCase,
) : ViewModel() {

    private val screen = Screen.MVVM

    private val _state = mutableStateOf(CounterState(count = getCounterUseCase.invoke(screen)))
    val state: State<CounterState> = _state

    private val _inputValue = mutableStateOf("")
    val inputValue: State<String> = _inputValue

    fun increment() {
        val newCount = incrementUseCase(screen)
        _state.value = _state.value.copy(count = newCount)
    }

    fun decrement() {
        val newCount = decrementUseCase(screen)
        _state.value = _state.value.copy(count = newCount)
    }

    fun onEnter(input: String) {
    }

    fun onValueChange(newInput: String) {
        _inputValue.value = newInput
    }
}
