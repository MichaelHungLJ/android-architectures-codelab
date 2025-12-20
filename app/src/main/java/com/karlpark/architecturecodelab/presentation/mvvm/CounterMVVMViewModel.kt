package com.karlpark.architecturecodelab.presentation.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.karlpark.architecturecodelab.domain.CounterState
import com.karlpark.architecturecodelab.domain.DecrementCounterUseCase
import com.karlpark.architecturecodelab.domain.GetCounterUseCase
import com.karlpark.architecturecodelab.domain.IncrementCounterUseCase
import com.karlpark.architecturecodelab.domain.UpdateCounterInputUseCase
import com.karlpark.architecturecodelab.presentation.Screen

class CounterMVVMViewModel(
    private val incrementUseCase: IncrementCounterUseCase,
    private val decrementUseCase: DecrementCounterUseCase,
    private val updateCounterInputUseCase: UpdateCounterInputUseCase,
    getCounterUseCase: GetCounterUseCase,
) : ViewModel() {

    private val screen = Screen.MVVM

    private val _state = mutableStateOf(CounterState(count = getCounterUseCase.invoke(screen)))
    val state: State<CounterState> = _state

    private val _inputValue = mutableStateOf("")
    val inputValue: State<String> = _inputValue

    private val stack = mutableListOf<Int>()

    fun increment() {
        val newCount = incrementUseCase(screen)
        stack.add(1)
        _state.value = _state.value.copy(count = newCount)
    }

    fun decrement() {
        val newCount = decrementUseCase(screen)
        stack.add(-1)
        _state.value = _state.value.copy(count = newCount)
    }

    fun onEnter(input: String) {
        val trimInput = input.trim()
        val inputInt = trimInput.toIntOrNull()

        if (inputInt != null) {
            // add to count
            val newCount = updateCounterInputUseCase.invoke(screen, inputInt)
            stack.add(inputInt)
            _state.value = _state.value.copy(count = newCount)

            // clear state
            _inputValue.value = ""
        }
    }

    fun undoCount() {
        if (stack.isEmpty()) return

        val lastIndex = stack.lastIndex
        val lastCount = stack.removeAt(lastIndex)
        val newCount = updateCounterInputUseCase.invoke(screen, -lastCount)
        _state.value = _state.value.copy(count = newCount)
    }

    fun onValueChange(newInput: String) {
        _inputValue.value = newInput
    }
}
