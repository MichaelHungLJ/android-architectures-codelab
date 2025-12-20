package com.karlpark.architecturecodelab.presentation.mvp

import com.karlpark.architecturecodelab.domain.DecrementCounterUseCase
import com.karlpark.architecturecodelab.domain.GetCounterUseCase
import com.karlpark.architecturecodelab.domain.IncrementCounterUseCase
import com.karlpark.architecturecodelab.domain.UpdateCounterUseCase
import com.karlpark.architecturecodelab.presentation.Screen

class CounterMvpPresenter(
    private val getCounterUseCase: GetCounterUseCase,
    private val updateCounterUseCase: UpdateCounterUseCase,
) : CounterMvpContract.Presenter {

    private val screen = Screen.MVP
    private var view: CounterMvpContract.View? = null
    private val currentCount: Int
        get() = getCounterUseCase.invoke(screen)
    private var currentInput: String = ""

    private val stack = mutableListOf<Int>()

    override fun attach(view: CounterMvpContract.View) {
        this.view = view
        view.displayCount(currentCount)
        view.displayInput(currentInput)
    }
    override fun detach() { this.view = null }

    override fun onIncrementClicked() {
        updateCounterUseCase(screen, INCREMENT_COUNT_BY_ONE)
        stack.add(INCREMENT_COUNT_BY_ONE)
        view?.displayCount(currentCount) // Direct View update
    }
    override fun onDecrementClicked() {
        updateCounterUseCase(screen, DECREMENT_COUNT_BY_ONE)
        stack.add(DECREMENT_COUNT_BY_ONE)
        view?.displayCount(currentCount) // Direct View update
    }

    override fun onEnter(input: String) {
        val inputInt = input.trim().toIntOrNull() ?: return

        updateCounterUseCase.invoke(screen, inputInt)
        stack.add(inputInt)
        view?.displayCount(currentCount)

        currentInput = ""
        view?.displayInput(currentInput)
    }

    override fun onValueChange(input: String) {
        currentInput = input
        view?.displayInput(input)
    }

    override fun onUndo() {
        if (stack.isEmpty()) return

        val lastCount = stack.removeAt(stack.lastIndex)
        updateCounterUseCase.invoke(screen, -lastCount)
        view?.displayCount(currentCount)
    }

    companion object CONSTANT {
        const val INCREMENT_COUNT_BY_ONE = 1
        const val DECREMENT_COUNT_BY_ONE = -1
    }
}
