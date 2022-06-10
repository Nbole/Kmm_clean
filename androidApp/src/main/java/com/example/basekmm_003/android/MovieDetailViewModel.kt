package com.example.basekmm_003.android

import androidx.lifecycle.ViewModel
import com.example.basekmm_003.data.remote.WResponse
import com.example.basekmm_003.domain.MovieUseCase
import kotlinx.coroutines.flow.Flow
import name.PreviewMovieEntity
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieDetailViewModel(get()) }
}

class MovieDetailViewModel(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getMovies(): Flow<WResponse<List<PreviewMovieEntity>>> = movieUseCase.getLatestMovies()
}
