package com.example.basekmm_003.android

interface UiState
interface UiEvent
interface UiEffect

sealed interface BaseState<out T> {
    data class Success<out T>(val data: T) : BaseState<T>
    data class Loading<out T>(val data: T?) : BaseState<T>
    object Empty : BaseState<Nothing>
    object Idle : BaseState<Nothing>
    object Error : BaseState<Nothing>
}
