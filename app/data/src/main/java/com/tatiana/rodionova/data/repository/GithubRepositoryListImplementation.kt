package com.tatiana.rodionova.data.repository

import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.mapper.toDomain
import com.tatiana.rodionova.data.model.GithubItem
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.repository.GithubListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GithubRepositoryListImplementation(
    private val key: String,
    private val githubService: GithubService
) : GithubListRepository {

    override suspend fun getGithubAndroidRepositories(): Flow<List<GithubRepositoryListDomainItem>> =
        flow {
            emit(
                githubService
                    .getRepositories(key)
                    .items
                    .map(GithubItem::toDomain)
            )
        }.flowOn(Dispatchers.Default)
}