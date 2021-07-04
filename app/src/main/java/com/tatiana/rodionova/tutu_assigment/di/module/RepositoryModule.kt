package com.tatiana.rodionova.tutu_assigment.di.module

import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.repository.GithubRepositoryImplementation
import com.tatiana.rodionova.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun providesGithubRepository(githubService: GithubService, key: String): GithubRepository {
        return GithubRepositoryImplementation(key, githubService)
    }
}