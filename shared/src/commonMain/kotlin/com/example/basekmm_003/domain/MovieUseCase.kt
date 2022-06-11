package com.example.basekmm_003.domain

import com.example.basekmm_003.data.MovieRepository
import com.example.basekmm_003.data.remote.WResponse
import kotlinx.coroutines.flow.Flow
import name.PreviewMovieEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

val useCaseModule = module {
    single { MovieUseCase() }
}

interface UseCaseContract {
    fun getLatestMovies(): Flow<WResponse<List<PreviewMovieEntity>>>
}

class MovieUseCase : UseCaseContract, KoinComponent {
    private val movieRepository: MovieRepository by inject()
    override fun getLatestMovies(): Flow<WResponse<List<PreviewMovieEntity>>> =
        movieRepository.getLatestMovies()
}
