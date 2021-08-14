package com.woigt.jerawatchlist.api

import com.google.gson.annotations.SerializedName
import com.woigt.jerawatchlist.model.Movie

/**
 * Data class to instantiate the API response
 */
data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)
