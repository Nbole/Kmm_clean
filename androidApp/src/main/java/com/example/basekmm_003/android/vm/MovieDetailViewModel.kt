package com.example.basekmm_003.android.vm

import androidx.lifecycle.viewModelScope
import com.example.basekmm_003.android.ui.BaseState
import com.example.basekmm_003.android.MovieStates
import com.example.basekmm_003.data.remote.WResponse
import com.example.basekmm_003.domain.MovieUseCase
import com.example.basekmm_003.presentation.PreviewMovieDisplay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import name.PreviewMovieEntity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieDetailViewModel(get()) }
}

class MovieDetailViewModel(private val movieUseCase: MovieUseCase) :
    BaseViewModel<MovieStates.Event, MovieStates.State, MovieStates.Effect>() {
    init {
        viewModelScope.launch {
            val l: Flow<WResponse<List<PreviewMovieEntity>>> = movieUseCase.getLatestMovies()
            l.collect { result ->

                val state: MovieStates.State = when (result) {
                    is WResponse.Success -> {
                        MovieStates.State.ShowLatestMovies(
                            BaseState.Success(
                                data = result.data.map {
                                    PreviewMovieDisplay(
                                        id = it.id,
                                        title = it.title,
                                        posterPath = it.posterPath
                                    )
                                }
                            )
                        )
                    }
                    is WResponse.Loading -> {
                        MovieStates.State.ShowLatestMovies(
                            BaseState.Loading(
                                data = result.data?.map {
                                    PreviewMovieDisplay(
                                        id = it.id,
                                        title = it.title,
                                        posterPath = it.posterPath
                                    )
                                }
                            )
                        )
                    }
                    is WResponse.Error -> {
                        MovieStates.State.ShowLatestMovies(
                            BaseState.Error
                        )
                    }
                }
                setState {
                    state
                }
            }
        }
    }

    override fun createInitialState(): MovieStates.State = MovieStates.State.ShowLatestMovies(
        BaseState.Idle
    )

    override fun handleEvent(event: MovieStates.Event) {
        when (event) {
            is MovieStates.Event.OnMovieSelected -> {
                setEffect {
                    MovieStates.Effect.GoToDetailMovie(event.id)
                }
            }
        }
    }
}

