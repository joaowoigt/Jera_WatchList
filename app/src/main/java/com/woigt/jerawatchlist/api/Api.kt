package com.woigt.jerawatchlist.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies (
        @Query("api_key") apiKey: String = "7082818e866b5e8acc5bf39d3f78301a",
        @Query("page") page: Int
    ) : Call<GetMoviesResponse>

    
}