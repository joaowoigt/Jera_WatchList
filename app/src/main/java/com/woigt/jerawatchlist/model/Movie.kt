package com.woigt.jerawatchlist.model


import com.google.gson.annotations.SerializedName

/**
 *  Model class for a Movie
 *
 *  @param title title of the movie
 *  @param posterPath URL path to the poster image
 *  @param genre_ids List of Integer that represent the genre of the movie on the TMDB api
 */

data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,

){

    /**
     * The empty constructor is necessary to the Firestore Database
     */
    constructor() : this("", "", emptyList<Int>())
}
