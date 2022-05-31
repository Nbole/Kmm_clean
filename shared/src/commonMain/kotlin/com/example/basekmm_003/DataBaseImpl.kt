package com.example.basekmm_003
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import name.PreviewMovieEntity

interface MovieDataBase {
    suspend fun deleteAllPreviewMovies()
    suspend fun savePreviewMovies(input: List<PreviewMovieEntity>)
    fun loadAllPreviewMovies(): Flow<List<PreviewMovieEntity>>
}

class DataBaseImpl(
    databaseDriverFactory: DatabaseDriverFactory
) : MovieDataBase {
    private val database = AppDatabase.invoke(databaseDriverFactory.createDriver())
    private val queries = database.appDataBaseQueries
    init {

        //AppDatabase.invoke(databaseDriverFactory.createDriver())
    }

    override fun loadAllPreviewMovies(): Flow<List<PreviewMovieEntity>> {
        return flow { emit(queries.getAllPreviewMovies().executeAsList()) }
    }

    override suspend fun deleteAllPreviewMovies() {
        withContext(Dispatchers.Default) { queries.deleteAllPreviewMovie() }
    }

    override suspend fun savePreviewMovies(input: List<PreviewMovieEntity>) {
        withContext(Dispatchers.Default) {
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