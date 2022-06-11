package com.example.basekmm_003.data

import com.example.basekmm_003.data.local.MovieDao
import com.example.basekmm_003.data.remote.SerialResponse
import com.example.basekmm_003.data.remote.MovieRemote
import com.example.basekmm_003.data.remote.WResponse
import com.example.basekmm_003.domain.Contractors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import name.PreviewMovieEntity
import org.koin.core.component.KoinComponent
import org.koin.dsl.module

val movieRepositoryModule = module {
    single { MovieRepository(get(), get()) }
}

class MovieRepository(
    private val movieRemote: MovieRemote,
    private val movieDao: MovieDao
) : Contractors, KoinComponent {
    override fun getLatestMovies(): Flow<WResponse<List<PreviewMovieEntity>>> =
        networkBoundResource(
            { movieDao.loadAllPreviewMovies() },
            { movieRemote.getLatestMovies() },
            { response ->
                if (response is SerialResponse.Success && response.data != null) {
                    movieDao.updatePreviewMovies(
                        response.data.results.map {
                            PreviewMovieEntity(
                                id = it.id.toLong(),
                                title = it.title,
                                posterPath = it.posterPath,
                            )
                        }
                    )
                } else {
                    movieDao.deleteAllPreviewMovies()
                }
            }
        ).flowOn(Dispatchers.Default)
}