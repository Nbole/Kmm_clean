package com.example.basekmm_003.data.vo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewMovie(
    val id: Int,
    @SerialName("original_title") val title: String?,
    @SerialName("poster_path") val posterPath: String?,
)
