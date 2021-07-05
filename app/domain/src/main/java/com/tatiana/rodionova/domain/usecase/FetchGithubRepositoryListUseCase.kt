package com.tatiana.rodionova.domain.usecase

import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.repository.GithubListRepository
import kotlinx.coroutines.flow.Flow

class FetchGithubRepositoryListUseCase(private val githubRepository: GithubListRepository) {

    suspend operator fun invoke(): Flow<List<GithubRepositoryListDomainItem>> =
        githubRepository.getGithubAndroidRepositories()
}