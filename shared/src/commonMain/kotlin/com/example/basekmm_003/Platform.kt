package com.example.basekmm_003

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

expect class MainDispatcher() {
    val dispatcher: CoroutineDispatcher
}

interface JsonApi {
    fun getHttpClient(): HttpClient
}

/*interface MovieApi {
    suspend fun getLatestMovies(): List<PreviewMovie>
}

class MovieApiImpl: MovieApi, KoinComponent {
    private val movieDao: MovieRemote by inject()
    private val ieCache: MovieDao by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getLatestMovies(): List<PreviewMovie> {
        ieCache.deleteAllPreviewMovies()
        val l: PreviewMovieResult? = movieDao.getLatestMovies()
        ieCache.updatePreviewMovies(
            l?.results.orEmpty().map {
                PreviewMovieEntity(
                    id = it.id.toLong(),
                    title = it.title,
                    posterPath = it.posterPath
                )
            }
        )
        val o: List<PreviewMovie> = ieCache.loadAllPreviewMovies().firstOrNull().orEmpty().map {
            PreviewMovie(
                id = it.id.toInt(),
                title = it.title,
                posterPath = it.posterPath
            )
        }
        return suspendCancellableCoroutine {
            it.resume(o, null)
        }
    }
} */
