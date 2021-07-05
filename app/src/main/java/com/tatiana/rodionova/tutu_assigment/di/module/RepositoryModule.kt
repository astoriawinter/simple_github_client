package com.tatiana.rodionova.tutu_assigment.di.module

import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.repository.GithubRepositoryDetailedImplementation
import com.tatiana.rodionova.data.repository.GithubRepositoryListImplementation
import com.tatiana.rodionova.domain.repository.GithubDetailedRepository
import com.tatiana.rodionova.domain.repository.GithubListRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun providesGithubListRepository(githubService: GithubService, key: String): GithubListRepository {
        return GithubRepositoryListImplementation(key, githubService)
    }

    @Provides
    fun providesGithubDetailedRepository(githubService: GithubService): GithubDetailedRepository {
        return GithubRepositoryDetailedImplementation(githubService)
    }
}