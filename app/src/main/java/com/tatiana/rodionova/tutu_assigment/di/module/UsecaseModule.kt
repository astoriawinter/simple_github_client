package com.tatiana.rodionova.tutu_assigment.di.module

import com.tatiana.rodionova.domain.repository.GithubRepository
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryListUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class UsecaseModule {
    @Provides
    fun providesFetchGithubRepositoryListUseCase(
        githubRepository: GithubRepository
    ): FetchGithubRepositoryListUseCase {
        return FetchGithubRepositoryListUseCase(githubRepository)
    }
}