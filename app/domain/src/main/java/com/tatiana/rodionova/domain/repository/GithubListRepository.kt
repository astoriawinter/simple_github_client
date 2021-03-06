package com.tatiana.rodionova.domain.repository

import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import kotlinx.coroutines.flow.Flow

interface GithubListRepository {
    fun getGithubAndroidRepositories() : Flow<List<GithubRepositoryListDomainItem>>
}