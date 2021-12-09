package dev.fstudio.coffeebreaker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _messageUiState = MutableStateFlow("0")
    val messageUiState: StateFlow<String> = _messageUiState

    fun messageLoop() = viewModelScope.launch {
        for (i in 1..5) {
            _messageUiState.value = i.toString()
            delay(200)
        }
    }

}