package com.tatiana.rodionova.domain.repository

import com.tatiana.rodionova.domain.model.GithubRepositoryDomainItem
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getGithubAndroidRepositories() : Flow<List<GithubRepositoryDomainItem>>
}