package com.woigt.jerawatchlist.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
/**
 *  Interface to TMDB api
 */

interface Api {

    /**
     * @return all popular movies based on the Api
     */
    @GET("movie/popular")
    fun getPopularMovies (
        @Query("api_key") apiKey: String = "7082818e866b5e8acc5bf39d3f78301a",
        @Query("page") page: Int
    ) : Call<GetMoviesResponse>

    /**
     * @return movies with the inserted term
     */
    @GET("search/movie")
    fun searchMovies (
        @Query("api_key") apiKey: String = "7082818e866b5e8acc5bf39d3f78301a",
        @Query("query") search: String?,
        @Query("page") page: Int
    ) : Call<GetMoviesResponse>

    /**
     * @return movies with the inserted genre
     */
    @GET("discover/movie")
    fun discoverMovie (
        @Query("api_key") apiKey: String = "7082818e866b5e8acc5bf39d3f78301a",
        @Query("with_genres") with_genres: String?,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}