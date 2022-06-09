package com.example.basekmm_003.data.local

import com.example.basekmm_003.AppDatabase
import com.example.basekmm_003.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import name.PreviewMovieEntity
import org.koin.dsl.module

val movieDaoModule = module {
    single<MovieDao> { MovieDaoImpl(get()) }
}

interface MovieDao {
    fun deleteAllPreviewMovies()
    suspend fun updatePreviewMovies(input: List<PreviewMovieEntity>)
    fun loadAllPreviewMovies(): Flow<List<PreviewMovieEntity>>
}

class MovieDaoImpl(databaseDriverFactory: DatabaseDriverFactory) : MovieDao {
    private val database = AppDatabase.invoke(databaseDriverFactory.createDriver())
    private val queries = database.appDataBaseQueries

    override fun loadAllPreviewMovies(): Flow<List<PreviewMovieEntity>> {
        return flow { emit(queries.getAllPreviewMovies().executeAsList()) }
    }

    override fun deleteAllPreviewMovies() {
         queries.deleteAllPreviewMovie()
    }

    override suspend fun updatePreviewMovies(input: List<PreviewMovieEntity>) {
        withContext(Dispatchers.Default) {
            queries.transaction {
                deleteAllPreviewMovies()
                input.map {
                    queries.savePreviewMovies(
                        id = it.id,
                        posterPath = it.posterPath,
                        title = it.title
                    )
                }
            }
        }
    }
}