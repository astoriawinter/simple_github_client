package com.tatiana.rodionova.data.repository

import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.api.helper.NetworkHelper
import com.tatiana.rodionova.data.database.dao.TreeItemDao
import com.tatiana.rodionova.data.mapper.toDomain
import com.tatiana.rodionova.data.mapper.toEntity
import com.tatiana.rodionova.data.model.dto.Tree
import com.tatiana.rodionova.data.model.entity.TreeItemEntity
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.model.getFullRepositoryName
import com.tatiana.rodionova.domain.repository.GithubDetailedRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class GithubRepositoryDetailedImplementation(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val githubService: GithubService,
    private val githubTreeItemDao: TreeItemDao,
    private val networkHelper: NetworkHelper
) : GithubDetailedRepository {

    override fun getGithubRepositoryTrees(
        name: String,
        repo: String
    ): Flow<List<GithubRepositoryTreeDomainItem>> =
        if (networkHelper.isNetworkConnected.value) {
            getTreeListFromApiAndUpdateDBAsFlow(name, repo)
        } else getTreeListFromDBAndUpdateFromApi(name, repo)

    private suspend fun getTreeListFromApi(name: String, repo: String): List<Tree> =
        githubService
            .getTreeStructure(name, repo)
            .tree

    private fun getTreeListFromApiAndUpdateDB(
        name: String,
        repo: String
    ): Flow<List<GithubRepositoryTreeDomainItem>> =
        flow {
            val treeList = getTreeListFromApi(name, repo)
            emit(treeList.map(Tree::toDomain))

            githubTreeItemDao.saveTreeList(
                treeList.map { tree ->
                    tree.toEntity(getFullRepositoryName(name, repo))
                }
            )
        }

    private fun getTreeListFromApiAndUpdateDBAsFlow(
        name: String,
        repo: String
    ): Flow<List<GithubRepositoryTreeDomainItem>> =
        getTreeListFromApiAndUpdateDB(name, repo).flowOn(dispatcher)

    private fun getTreeListFromDBAndUpdateFromApi(
        name: String,
        repo: String
    ): Flow<List<GithubRepositoryTreeDomainItem>> =
        networkHelper.isNetworkConnected
            .flatMapLatest { isConnected ->
                if (isConnected) {
                    getTreeListFromApiAndUpdateDB(name, repo)
                } else {
                    githubTreeItemDao.getTreeListByNameAndRepo(getFullRepositoryName(name, repo))
                        .map { treeList ->
                            treeList.map(TreeItemEntity::toDomain)
                        }
                }
            }.flowOn(dispatcher)
}
