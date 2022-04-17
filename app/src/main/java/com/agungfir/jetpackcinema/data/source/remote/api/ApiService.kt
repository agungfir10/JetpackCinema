package com.agungfir.jetpackcinema.data.source.remote.api

import com.agungfir.jetpackcinema.BuildConfig
import com.agungfir.jetpackcinema.data.source.remote.response.MovieResponse
import com.agungfir.jetpackcinema.data.source.remote.response.Response
import com.agungfir.jetpackcinema.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    fun getDiscoverMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<Response<MovieResponse>>

    @GET("discover/tv")
    fun getDiscoverTvShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<Response<TvShowResponse>>

}