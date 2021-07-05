package com.tatiana.rodionova.data.api

import com.tatiana.rodionova.data.model.GithubRepositoryModel
import com.tatiana.rodionova.data.model.TreeStructureModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") key: String): GithubRepositoryModel

    @GET("repos/{name}/{repo}/git/trees/master")
    suspend fun getTreeStructure(
        @Path("name") name: String,
        @Path("repo") repo: String
    ): TreeStructureModel
}