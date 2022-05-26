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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

expect class Platform() {
    val platform: String
}

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

interface JsonApi {
    fun getHttpClient(): HttpClient
}
class JsonApiImpl: JsonApi {
    override fun getHttpClient(): HttpClient = KtorClient.httpClient
}

fun provideHttpClient(): HttpClient = JsonApiImpl().getHttpClient()

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

class MovieApiImpl: KoinComponent {
    private val httpClient: HttpClient by inject()

    suspend fun getLatestMovies(): PreviewMovieResult =
        httpClient.get {
            url("https://api.themoviedb.org/3/" + "movie/now_playing")
            parameter("api_key", "5e30e8afd06d2b8b9aae8eb164c85a29")
        }
}

/*
class MovieApiImpl(private val httpClient: HttpClient) : MovieApiContract {
    override suspend fun getLatestMovies(): PreviewMovieResult =
        httpClient.get {
            url("https://api.themoviedb.org/3/" + "movie/now_playing")
            parameter("api_key", "5e30e8afd06d2b8b9aae8eb164c85a29")
        }
}
*/
@Serializable
data class PreviewMovieResult(val results: List<PreviewMovie>)

@Serializable
data class PreviewMovie(
    val id: Int,
    @SerialName("original_title") val title: String?,
    @SerialName("poster_path") val posterPath: String?,
)
