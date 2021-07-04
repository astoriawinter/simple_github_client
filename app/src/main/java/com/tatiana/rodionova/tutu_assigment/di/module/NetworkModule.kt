package com.tatiana.rodionova.tutu_assigment.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tatiana.rodionova.data.api.GithubService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesKey(): String = KEY_ANDROID

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor =
        Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(ACCEPT_HEADER_NAME, GITHUB_ACCEPT_HEADER)
                    .build()
            )
        }

    @Provides
    @Singleton
    fun createClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            addInterceptor(interceptor)
        }.build()

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient, gson: Gson): GithubService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GithubService::class.java)

    companion object {
        const val ACCEPT_HEADER_NAME = "Accept"
        const val GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json"
        const val KEY_ANDROID = "android"
        const val NETWORK_TIME_OUT: Long = 15
        const val BASE_URL = "https://api.github.com/"
    }
}
