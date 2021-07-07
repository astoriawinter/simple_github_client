package com.tatiana.rodionova.domain.usecase

import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.repository.GithubDetailedRepository
import kotlinx.coroutines.flow.Flow

class FetchGithubRepositoryTreeUseCase(private val githubRepository: GithubDetailedRepository) {

    operator fun invoke(
        name: String,
        repo: String
    ): Flow<List<GithubRepositoryTreeDomainItem>> =
        githubRepository.getGithubRepositoryTrees(name, repo)
}