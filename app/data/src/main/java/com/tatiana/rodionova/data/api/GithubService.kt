package com.tatiana.rodionova.data.api

import com.tatiana.rodionova.data.model.GithubRepositoryModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") key: String): GithubRepositoryModel
}