package com.example.basekmm_003

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import name.PreviewMovieEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

expect class MainDispatcher() {
    val dispatcher: CoroutineDispatcher
}

interface JsonApi {
    fun getHttpClient(): HttpClient
}

class JsonApiImpl : JsonApi, KoinComponent {
    override fun getHttpClient(): HttpClient = KtorClient.httpClient
}

fun provideHttpClient(): HttpClient = JsonApiImpl().getHttpClient()

val commonModule = module {
    single { provideHttpClient() }
    single<MovieDataBase> { DataBaseImpl(get()) }
    single<MovieRemote> { MovieRemoteImpl(get()) }
    single<MovieApi> { MovieApiImpl() }
}

interface MovieRemote {
    suspend fun getLatestMovies(): PreviewMovieResult?
}

class MovieRemoteImpl(private val httpClient: HttpClient) : MovieRemote {

    override suspend fun getLatestMovies(): PreviewMovieResult? {
        return try {
            httpClient.get {
                url("https://api.themoviedb.org/3/" + "movie/now_playing")
                parameter("api_key", "5e30e8afd06d2b8b9aae8eb164c85a29")
            }
        } catch (e: Exception) {
            null
        }
    }
}

interface MovieApi {
    suspend fun getLatestMovies(): List<PreviewMovieEntity>
}

class MovieApiImpl: MovieApi, KoinComponent {
    private val movieDao: MovieRemote by inject()
    private val ieCache: MovieDataBase by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getLatestMovies(): List<PreviewMovieEntity> {
        ieCache.deleteAllPreviewMovies()
        val l: PreviewMovieResult? = movieDao.getLatestMovies()
        ieCache.savePreviewMovies(
            l?.results.orEmpty().map {
                PreviewMovieEntity(
                    id = it.id.toLong(),
                    title = it.title,
                    posterPath = it.posterPath
                )
            }
        )
        val o: List<PreviewMovieEntity> = ieCache.loadAllPreviewMovies().firstOrNull().orEmpty()
        return suspendCancellableCoroutine {
            it.resume(o, null)
        }
    }
}

@Serializable
data class PreviewMovieResult(val results: List<PreviewMovie>)

@Serializable
data class PreviewMovie(
    val id: Int,
    @SerialName("original_title") val title: String?,
    @SerialName("poster_path") val posterPath: String?,
)

@Serializable
data class JsonMessage(val string: String?, val lang: String?)

object KtorClient {
    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
    val httpClient = HttpClient {
        install(JsonFeature) { serializer = KotlinxSerializer(json) }
        install(HttpTimeout) {
            socketTimeoutMillis = 30_000
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
