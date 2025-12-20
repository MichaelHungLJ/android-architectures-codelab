package com.karlpark.architecturecodelab.presentation.mvp

import com.karlpark.architecturecodelab.domain.DecrementCounterUseCase
import com.karlpark.architecturecodelab.domain.GetCounterUseCase
import com.karlpark.architecturecodelab.domain.IncrementCounterUseCase
import com.karlpark.architecturecodelab.domain.UpdateCounterInputUseCase
import com.karlpark.architecturecodelab.presentation.Screen

class CounterMvpPresenter(
    private val incrementUseCase: IncrementCounterUseCase,
    private val decrementUseCase: DecrementCounterUseCase,
    private val getCounterUseCase: GetCounterUseCase,
    private val updateCounterInputUseCase: UpdateCounterInputUseCase,
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
        incrementUseCase(screen)
        stack.add(1)
        view?.displayCount(currentCount) // Direct View update
    }
    override fun onDecrementClicked() {
        decrementUseCase(screen)
        stack.add(-1)
        view?.displayCount(currentCount) // Direct View update
    }

    override fun onEnter(input: String) {
        val inputInt = input.trim().toIntOrNull() ?: return

        updateCounterInputUseCase.invoke(screen, inputInt)
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
        updateCounterInputUseCase.invoke(screen, -lastCount)
        view?.displayCount(currentCount)
    }
}
