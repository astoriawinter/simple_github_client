package com.tatiana.rodionova.tutu_assigment.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.api.helper.NetworkHelper
import com.tatiana.rodionova.tutu_assigment.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    @Named("key")
    fun providesKey(): String = KEY_ANDROID

    @Provides
    @Singleton
    @Named("sort_by")
    fun providesSortBy(): String = SORT_BY

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor =
        Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(
                        AUTHORIZATION_HEADER_NAME,
                        Credentials.basic(
                            BuildConfig.LOGIN,
                            BuildConfig.TOKEN
                        )
                    )
                    .header(ACCEPT_HEADER_NAME, GITHUB_ACCEPT_HEADER)
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

    @Singleton
    @Provides
    fun provideNetworkHelper(context: Context): NetworkHelper =
        NetworkHelper(context)

    companion object {
        private const val ACCEPT_HEADER_NAME = "Accept"
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
        private const val GITHUB_ACCEPT_HEADER = "application/vnd.github.v3+json"
        private const val KEY_ANDROID = "android"
        private const val SORT_BY = "stars"
        private const val NETWORK_TIME_OUT: Long = 15
        private const val BASE_URL = "https://api.github.com/"
    }
}
