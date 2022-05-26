package com.example.basekmm_003

import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule)
}

// called by iOS etc
fun initKoin() = initKoin{}

val commonModule = module {
    single { provideHttpClient() }
    //single { provideMovieApi(get()) }
}

/*
fun provideHttpClient(): HttpClient {
    val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
    return HttpClient {
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
}*/

//fun provideMovieApi(httpClient: HttpClient): MovieApiContract = MovieApiImpl(httpClient)
