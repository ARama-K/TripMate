package com.example.travelapp.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("customsearch/v1")
    fun customSearch(
        @Query("key") apiKey: String,
        @Query("cx") searchIdCx: String,
        @Query("q") keyword: String
    ): Call<SearchResponse>
}
