package com.tatiana.rodionova.data.api

import com.tatiana.rodionova.data.model.dto.GithubRepositoryModel
import com.tatiana.rodionova.data.model.dto.TreeStructureModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") key: String,
        @Query("sort") sortBy: String
    ): GithubRepositoryModel

    @GET("repos/{name}/{repo}/git/trees/master")
    suspend fun getTreeStructure(
        @Path("name") name: String,
        @Path("repo") repo: String
    ): TreeStructureModel
}