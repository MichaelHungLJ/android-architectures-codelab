package com.karlpark.architecturecodelab.presentation.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.karlpark.architecturecodelab.domain.CounterState
import com.karlpark.architecturecodelab.domain.DecrementCounterUseCase
import com.karlpark.architecturecodelab.domain.GetCounterUseCase
import com.karlpark.architecturecodelab.domain.IncrementCounterUseCase
import com.karlpark.architecturecodelab.domain.UpdateCounterUseCase
import com.karlpark.architecturecodelab.presentation.Screen

class CounterMVVMViewModel(
    private val updateCounterUseCase: UpdateCounterUseCase,
    getCounterUseCase: GetCounterUseCase,
) : ViewModel() {

    private val screen = Screen.MVVM

    private val _state = mutableStateOf(CounterState(count = getCounterUseCase.invoke(screen)))
    val state: State<CounterState> = _state

    private val _inputValue = mutableStateOf("")
    val inputValue: State<String> = _inputValue

    private val stack = mutableListOf<Int>()

    fun increment() {
        val newCount = updateCounterUseCase(screen,INCREMENT_COUNT_BY_ONE)
        stack.add(INCREMENT_COUNT_BY_ONE)
        _state.value = _state.value.copy(count = newCount)
    }

    fun decrement() {
        val newCount = updateCounterUseCase(screen,DECREMENT_COUNT_BY_ONE)
        stack.add(DECREMENT_COUNT_BY_ONE)
        _state.value = _state.value.copy(count = newCount)
    }

    fun onEnter(input: String) {
        val trimInput = input.trim()
        val inputInt = trimInput.toIntOrNull()

        if (inputInt != null) {
            // add to count
            val newCount = updateCounterUseCase.invoke(screen, inputInt)
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
        val newCount = updateCounterUseCase.invoke(screen, -lastCount)
        _state.value = _state.value.copy(count = newCount)
    }

    fun onValueChange(newInput: String) {
        _inputValue.value = newInput
    }

    companion object CONSTANT {
        const val INCREMENT_COUNT_BY_ONE = 1
        const val DECREMENT_COUNT_BY_ONE = -1
    }
}
