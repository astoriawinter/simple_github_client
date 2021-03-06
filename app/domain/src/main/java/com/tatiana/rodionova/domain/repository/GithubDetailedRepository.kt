package com.tatiana.rodionova.domain.repository

import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import kotlinx.coroutines.flow.Flow

interface GithubDetailedRepository {
    fun getGithubRepositoryTrees(
        name: String,
        repo: String
    ): Flow<List<GithubRepositoryTreeDomainItem>>
}