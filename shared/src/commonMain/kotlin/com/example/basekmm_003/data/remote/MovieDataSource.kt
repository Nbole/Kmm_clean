package com.example.basekmm_003.data.remote

import com.example.basekmm_003.data.vo.PreviewMovieResult
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.utils.io.errors.IOException
import org.koin.dsl.module

val movieRemoteModule = module {
    single<MovieRemote> { MovieRemoteImpl(get()) }
}

interface MovieRemote { suspend fun getLatestMovies(): SerialResponse<PreviewMovieResult?> }

class MovieRemoteImpl(private val httpClient: HttpClient) : MovieRemote {
    override suspend fun getLatestMovies(): SerialResponse<PreviewMovieResult?> {
        return try {
            SerialResponse.Success(
                httpClient.get {
                    url("https://api.themoviedb.org/3/" + "movie/now_playing")
                    parameter("api_key", "5e30e8afd06d2b8b9aae8eb164c85a29")
                }
            )
        } catch (e: ClientRequestException) {
            SerialResponse.Error(data = null, message = e.message)
        } catch (e: IOException) {
            SerialResponse.Error(data = null, message = e.message.orEmpty())
        }
    }
}