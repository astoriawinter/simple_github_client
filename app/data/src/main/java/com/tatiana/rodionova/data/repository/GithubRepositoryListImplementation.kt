package com.tatiana.rodionova.data.repository

import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.api.helper.NetworkHelper
import com.tatiana.rodionova.data.database.dao.GithubItemDao
import com.tatiana.rodionova.data.mapper.toDomain
import com.tatiana.rodionova.data.mapper.toEntity
import com.tatiana.rodionova.data.model.dto.GithubItem
import com.tatiana.rodionova.data.model.entity.GithubItemEntity
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.repository.GithubListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class GithubRepositoryListImplementation(
    private val key: String,
    private val sortBy: String,
    private val githubService: GithubService,
    private val githubItemDao: GithubItemDao,
    private val networkHelper: NetworkHelper
) : GithubListRepository {

    override fun getGithubAndroidRepositories(): Flow<List<GithubRepositoryListDomainItem>> =
        if (networkHelper.isNetworkConnected.value) {
            getGithubListFromApiAndUpdateDBAsFlow()
        } else getGithubListFromDBOrUpdateFromApi()

    private suspend fun getGithubListFromApi() =
        githubService
            .getRepositories(key, sortBy)
            .items

    private fun getGithubListFromApiAndUpdateDB(): Flow<List<GithubRepositoryListDomainItem>> =
        flow {
            val githubList = getGithubListFromApi()

            emit(githubList.map(GithubItem::toDomain))

            githubItemDao.saveGithubItemList(
                githubList.map(GithubItem::toEntity)
            )
        }

    private fun getGithubListFromApiAndUpdateDBAsFlow(): Flow<List<GithubRepositoryListDomainItem>> =
        getGithubListFromApiAndUpdateDB().flowOn(Dispatchers.IO)

    private fun getGithubListFromDBOrUpdateFromApi(): Flow<List<GithubRepositoryListDomainItem>> =
        networkHelper.isNetworkConnected
            .flatMapLatest { isConnected ->
                if (isConnected) {
                    getGithubListFromApiAndUpdateDB()
                } else {
                    githubItemDao.getGithubItemList().map { treeList ->
                        treeList.map(GithubItemEntity::toDomain)
                    }
                }
            }.flowOn(Dispatchers.IO)
}