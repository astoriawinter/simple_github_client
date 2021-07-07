package com.tatiana.rodionova.tutu_assigment.di.module

import com.tatiana.rodionova.domain.repository.GithubDetailedRepository
import com.tatiana.rodionova.domain.repository.GithubListRepository
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryListUseCase
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryTreeUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module(includes = [RepositoryModule::class])
class UsecaseModule {
    @Provides
    fun providesFetchGithubRepositoryListUseCase(
        githubRepository: GithubListRepository
    ): FetchGithubRepositoryListUseCase {
        return FetchGithubRepositoryListUseCase(githubRepository)
    }

    @Provides
    fun providesFetchGithubRepositoryTreeUseCase(
        githubRepository: GithubDetailedRepository
    ): FetchGithubRepositoryTreeUseCase {
        return FetchGithubRepositoryTreeUseCase(githubRepository)
    }

    @Provides
    fun providesDispatcher(): CoroutineDispatcher =
        Dispatchers.IO
}