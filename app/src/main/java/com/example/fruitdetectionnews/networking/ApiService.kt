package com.example.fruitdetectionnews

import com.example.fruitdetectionnews.viewmodel.PostModel
import com.example.fruitdetectionnews.viewmodel.PostModel2
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/fruit")
    fun getPost() : Call<MutableList<PostModel>>
}

interface ApiService2 {
    @GET("top-headlines")
    fun getPost(
        @Query("q") q: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ) : Call<PostModel2>
}