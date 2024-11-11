package com.example.musicapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers(""
    )
    @GET("search")
    fun getData(@Query("q") query: String):Call<MyData>
}