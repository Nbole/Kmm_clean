package com.example.basekmm_003.domain

import com.example.basekmm_003.data.MovieRepository
import com.example.basekmm_003.data.remote.WResponse
import kotlinx.coroutines.flow.Flow
import name.PreviewMovieEntity
import org.koin.dsl.module

val useCaseModule = module {
    single { MovieUseCase(get()) }
}

interface UseCaseContract {
    fun getLatestMovies(): Flow<WResponse<List<PreviewMovieEntity>>>
}

class MovieUseCase(private val movieRepository: MovieRepository) : UseCaseContract {
    override fun getLatestMovies(): Flow<WResponse<List<PreviewMovieEntity>>> =
        movieRepository.getLatestMovies()
}
