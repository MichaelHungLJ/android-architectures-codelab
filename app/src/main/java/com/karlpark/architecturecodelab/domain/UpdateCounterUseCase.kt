package com.karlpark.architecturecodelab.domain

import com.karlpark.architecturecodelab.presentation.Screen

class UpdateCounterUseCase(
    private val repository: CounterRepository
) {
    operator fun invoke(
        screen: Screen,
        inputValue: Int
    ) = repository.updateCountWithInput(screen.ordinal, inputValue)
}
