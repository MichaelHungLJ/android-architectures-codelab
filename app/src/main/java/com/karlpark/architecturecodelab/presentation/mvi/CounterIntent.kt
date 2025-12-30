package com.karlpark.architecturecodelab.presentation.mvi

sealed class CounterIntent {
    object Increment : CounterIntent()
    object Decrement : CounterIntent()
    object onEnter: CounterIntent()
    object Undo: CounterIntent()
    data class UpdateCounterInput(val value: String) : CounterIntent()
}
