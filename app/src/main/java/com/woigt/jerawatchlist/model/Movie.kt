package com.woigt.jerawatchlist.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    val isWatched: Boolean = false
)

