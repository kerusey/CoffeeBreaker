package dev.fstudio.coffeebreaker.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {
    fun emitNumbers(): Flow<Int> = flow{
        val numList = listOf(1,2,3,4,5,6,7)
        numList.forEach {
            delay(it*100L)
            emit(it)
        }
    }
}