package com.karlpark.architecturecodelab.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karlpark.architecturecodelab.domain.CounterState
import com.karlpark.architecturecodelab.domain.DecrementCounterUseCase
import com.karlpark.architecturecodelab.domain.GetCounterUseCase
import com.karlpark.architecturecodelab.domain.IncrementCounterUseCase
import com.karlpark.architecturecodelab.domain.UpdateCounterUseCase
import com.karlpark.architecturecodelab.presentation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CounterMVIReducer(
    getCounterUseCase: GetCounterUseCase,
    private val updateCounterUseCase: UpdateCounterUseCase,
) : ViewModel() {

    private val screen = Screen.MVI
    private val _state = MutableStateFlow(CounterState(getCounterUseCase.invoke(screen)))
    val state: StateFlow<CounterState> = _state.asStateFlow()

    private val _inputValue = MutableStateFlow("")
    val inputValue: StateFlow<String> = _inputValue.asStateFlow()

    private val stack = mutableListOf<Int>()

    fun processIntent(intent: CounterIntent) {
        viewModelScope.launch {
            val currentValue = _inputValue.value.toIntOrNull() ?: 0

           when (intent) {
                is CounterIntent.Increment -> {
                    stack.add(INCREMENT_COUNT_BY_ONE)
                    val newCount = updateCounterUseCase(screen, INCREMENT_COUNT_BY_ONE)
                    _state.value = _state.value.copy(count = newCount)
                }
                is CounterIntent.Decrement -> {
                    stack.add(DECREMENT_COUNT_BY_ONE)
                    val newCount = updateCounterUseCase(screen, DECREMENT_COUNT_BY_ONE)
                    _state.value = _state.value.copy(count = newCount)
                }
                is CounterIntent.onEnter -> {
                    stack.add(currentValue)
                    val newCount = updateCounterUseCase(screen,currentValue)
                    _state.value = _state.value.copy(count = newCount)
                    _inputValue.value = "" // Reset input field
                }

                is CounterIntent.Undo -> {
                    if (stack.isEmpty()) return@launch
                    val lastIndex = stack.lastIndex
                    val lastCount = stack.removeAt(lastIndex)
                    val newCount = updateCounterUseCase.invoke(screen, -lastCount)
                    _state.value = _state.value.copy(count = newCount)
                }

                is CounterIntent.UpdateCounterInput -> {
                    _inputValue.value = intent.value
                }
            }
        }
    }

    companion object CONSTANT {
        const val INCREMENT_COUNT_BY_ONE = 1
        const val DECREMENT_COUNT_BY_ONE = -1
    }
}
