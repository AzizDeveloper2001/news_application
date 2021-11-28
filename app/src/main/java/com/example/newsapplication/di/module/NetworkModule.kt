package com.example.newsapplication.di.module

import com.example.newsapplication.BuildConfig
import com.example.newsapplication.networking.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBASE_URL():String="https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConvertor():GsonConverterFactory= GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideLogingIntercepter():HttpLoggingInterceptor{
        var httpLoggingInterceptor=HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor):OkHttpClient{
        val okkhttpclient=OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
        return okkhttpclient.build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(baseurl:String,
    gsonConverterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService=retrofit.create(ApiService::class.java)
}