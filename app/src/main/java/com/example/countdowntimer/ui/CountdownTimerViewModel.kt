package com.example.countdowntimer.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.countdowntimer.MainActivity
import kotlinx.coroutines.delay

class CountdownTimerViewModel: ViewModel() {

    var countdownSeconds: Int by mutableStateOf(0)

    var page: MainActivity.Page by mutableStateOf(MainActivity.Page.InputScreen)

    val numbers = mutableListOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", ""
    )

    var fullNumbers: String by mutableStateOf("000000")

    var inputNumbers: String by mutableStateOf("")

    fun inputNumber(number: String) {
        if(inputNumbers.isEmpty() && "0" == number){
            return
        }
        if(inputNumbers.length < 6) {
            inputNumbers = inputNumbers.plus(number)
            fullNumbers = "000000".removeRange(0, inputNumbers.length).plus(inputNumbers)
        }
    }

    fun removeNumber() {
        val length = inputNumbers.length
        if(length > 0){
            inputNumbers = inputNumbers.removeRange(length - 1, length)
            fullNumbers = "000000".removeRange(0, inputNumbers.length).plus(inputNumbers)
        }
    }

    fun countdownOrStop() {
        when(page) {
            MainActivity.Page.InputScreen -> {
                if(inputNumbers.isNotEmpty()){
                    start()
                }
            }
            MainActivity.Page.CountdownScreen -> {
                preStop()
                stop()
            }
        }
    }

    fun start() {
        page = MainActivity.Page.CountdownScreen
        animateFinished = false
    }

    fun preStop() {
        countdownSeconds = 0
        animateFinished = true
    }

    fun stop() {
        page = MainActivity.Page.InputScreen
    }

    fun animateNow() {
        val hour = fullNumbers.substring(0, 2).toInt()
        val minute = fullNumbers.substring(2, 4).toInt()
        val second = fullNumbers.substring(4, 6).toInt()
        countdownSeconds = hour * 60 * 60 + minute * 60 + second
    }

    var animateFinished = false

}