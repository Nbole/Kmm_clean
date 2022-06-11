package com.example.basekmm_003.android

import com.example.basekmm_003.android.ui.BaseState
import com.example.basekmm_003.android.ui.UiEffect
import com.example.basekmm_003.android.ui.UiEvent
import com.example.basekmm_003.android.ui.UiState
import com.example.basekmm_003.presentation.PreviewMovieDisplay

interface MovieStates {
    sealed class Event : UiEvent {
        data class OnMovieSelected(val id: Int) : Event()
    }

    sealed class State: UiState {
        data class ShowLatestMovies(val lastMovies: BaseState<List<PreviewMovieDisplay>>) : State()
    }

    sealed class Effect: UiEffect {
        data class GoToDetailMovie(val id: Int): Effect()
    }
}
