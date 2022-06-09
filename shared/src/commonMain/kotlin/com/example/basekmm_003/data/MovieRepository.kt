package com.example.basekmm_003.data

import com.example.basekmm_003.data.local.MovieDao
import com.example.basekmm_003.data.remote.MovieRemote
import com.example.basekmm_003.data.remote.KtorResponse
import com.example.basekmm_003.data.remote.WResponse
import com.example.basekmm_003.domain.Contractors
import kotlinx.coroutines.flow.Flow
import name.PreviewMovieEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

val movieRepositoryModule = module {
    single { MovieRepository() }
}

class MovieRepository : Contractors, KoinComponent {
    private val movieRemote: MovieRemote by inject()
    private val movieDao: MovieDao by inject()

    override fun getLatestMovies(): Flow<WResponse<List<PreviewMovieEntity>?>> =
        networkBoundResource(
            { movieDao.loadAllPreviewMovies() },
            { movieRemote.getLatestMovies() },
            { response ->
                if (response is KtorResponse.Success && response.data != null) {
                    movieDao.deleteAllPreviewMovies()
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
        )
}