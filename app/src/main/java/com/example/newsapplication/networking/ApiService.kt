package com.example.newsapplication.networking

import com.example.newsapplication.models.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
   suspend fun gettopnews(
        @Query("country") country:String="us",
        @Query("category") category: String,
        @Query("apiKey") apiKey:String="dfd296e350784e46ac82f289b2c3ac06"): Response<NewsData>

    @GET("everything")
     suspend  fun getcategorynews(
        @Query("q") q:String,
        @Query("apiKey") apiKey:String="dfd296e350784e46ac82f289b2c3ac06"): Response<NewsData>
}