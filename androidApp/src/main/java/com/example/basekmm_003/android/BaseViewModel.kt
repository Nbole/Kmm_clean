package com.example.basekmm_003.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {
    private val initialState: State by lazy { createInitialState() }
    private val currentState: State get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    abstract fun createInitialState(): State

    private fun subscribeEvents() {
        viewModelScope.launch { event.collect { handleEvent(it) } }
    }

    abstract fun handleEvent(event: Event)

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    fun setState(reduce: State.() -> State) {
        _uiState.value = currentState.reduce()
    }

    fun setEffect(builder: () -> Effect) {
        viewModelScope.launch { _effect.send(builder()) }
    }
}
