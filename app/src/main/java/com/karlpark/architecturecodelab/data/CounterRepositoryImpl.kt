package com.karlpark.architecturecodelab.data

import com.karlpark.architecturecodelab.domain.CounterRepository

class CounterRepositoryImpl : CounterRepository {
    private val countMap = mutableMapOf<Int, Int>()

    override fun getCount(screenNumber: Int): Int {
        return countMap.getOrPut(screenNumber) { 0 }
    }

    override fun getInitialCount(): Int = 0

    override fun updateCount(screenNumber: Int, increment: Boolean): Int {
        val currentCount = countMap[screenNumber] ?: 0
        val updated = if (increment) currentCount + 1 else currentCount - 1
        countMap[screenNumber] = updated
        return updated
    }

    override fun updateCountWithInput(screenNumber: Int, inputValue: Int): Int {
        val currentCount = countMap[screenNumber] ?: 0
        val updated = currentCount + inputValue
        countMap[screenNumber] = updated
        return updated
    }
}
