package com.tatiana.rodionova.domain.usecase

import com.tatiana.rodionova.domain.model.GithubRepositoryDomainItem
import com.tatiana.rodionova.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

class FetchGithubRepositoryListUseCase(private val githubRepository: GithubRepository) {

    suspend operator fun invoke(): Flow<List<GithubRepositoryDomainItem>> =
        githubRepository.getGithubAndroidRepositories()
}