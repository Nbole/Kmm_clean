package com.example.basekmm_003.android.ui

interface UiState
interface UiEvent
interface UiEffect

sealed interface BaseState<out T> {
    data class Success<out T>(val data: T) : BaseState<T>
    data class Loading<out T>(val data: T?) : BaseState<T>
    object Idle : BaseState<Nothing>
    object Error : BaseState<Nothing>
}
