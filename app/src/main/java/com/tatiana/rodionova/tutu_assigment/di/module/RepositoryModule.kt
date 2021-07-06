package com.tatiana.rodionova.tutu_assigment.di.module

import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.api.helper.NetworkHelper
import com.tatiana.rodionova.data.database.dao.GithubItemDao
import com.tatiana.rodionova.data.database.dao.TreeItemDao
import com.tatiana.rodionova.data.repository.GithubRepositoryDetailedImplementation
import com.tatiana.rodionova.data.repository.GithubRepositoryListImplementation
import com.tatiana.rodionova.domain.repository.GithubDetailedRepository
import com.tatiana.rodionova.domain.repository.GithubListRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RepositoryModule {
    @Provides
    fun providesGithubListRepository(
        githubService: GithubService,
        @Named("key") key: String,
        @Named("sort_by") sortBy: String,
        githubItemDao: GithubItemDao,
        networkHelper: NetworkHelper
    ): GithubListRepository =
        GithubRepositoryListImplementation(key, sortBy, githubService, githubItemDao, networkHelper)

    @Provides
    fun providesGithubDetailedRepository(
        githubService: GithubService,
        githubTreeItemDao: TreeItemDao,
        networkHelper: NetworkHelper
    ): GithubDetailedRepository =
        GithubRepositoryDetailedImplementation(githubService, githubTreeItemDao, networkHelper)
}