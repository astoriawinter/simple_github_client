package com.tatiana.rodionova.data.repository

import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.mapper.toDomain
import com.tatiana.rodionova.data.model.Tree
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.repository.GithubDetailedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GithubRepositoryDetailedImplementation(
    private val githubService: GithubService
) : GithubDetailedRepository {

    override suspend fun getGithubRepositoryTrees(
        name: String,
        repo: String
    ): Flow<List<GithubRepositoryTreeDomainItem>> =
        flow {
            emit(
                githubService
                    .getTreeStructure(name, repo)
                    .tree
                    .map(Tree::toDomain)
            )
        }.flowOn(Dispatchers.Default)
}