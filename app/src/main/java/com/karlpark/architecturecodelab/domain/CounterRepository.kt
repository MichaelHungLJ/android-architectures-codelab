package com.karlpark.architecturecodelab.domain

interface CounterRepository {


    fun getInitialCount(): Int
    fun getCount(screenNumber: Int): Int
    fun updateCount(screenNumber: Int, increment: Boolean): Int
    fun updateCountWithInput(screenNumber: Int, inputValue: Int): Int
}
