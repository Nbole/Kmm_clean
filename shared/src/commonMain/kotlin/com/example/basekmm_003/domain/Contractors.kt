package com.example.basekmm_003.domain

import com.example.basekmm_003.data.remote.WResponse
import kotlinx.coroutines.flow.Flow
import name.PreviewMovieEntity

interface Contractors {
    fun getLatestMovies(): Flow<WResponse<List<PreviewMovieEntity>>>
}
