package com.tatiana.rodionova.tutu_assigment.data

import app.cash.turbine.test
import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.api.helper.NetworkHelper
import com.tatiana.rodionova.data.database.dao.TreeItemDao
import com.tatiana.rodionova.data.model.dto.RepositoryTypeModel
import com.tatiana.rodionova.data.model.dto.Tree
import com.tatiana.rodionova.data.model.dto.TreeStructureModel
import com.tatiana.rodionova.data.model.entity.TreeItemEntity
import com.tatiana.rodionova.data.repository.GithubRepositoryDetailedImplementation
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.model.RepositoryTypeDomain
import com.tatiana.rodionova.domain.model.getFullRepositoryName
import com.tatiana.rodionova.tutu_assigment.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class GithubRepositoryDetailedTest {
    private val githubService = mockk<GithubService>()
    private val githubTreeItemDao = mockk<TreeItemDao>()
    private val networkHelper = mockk<NetworkHelper>()
    private val name = "android"
    private val repo = "testRepo"

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()
    private val githubRepositoryDetailed = spyk(
        GithubRepositoryDetailedImplementation(
            coroutinesTestRule.testCoroutineDispatcher,
            githubService,
            githubTreeItemDao,
            networkHelper
        )
    )

    @Test
    fun testRepositoryWhenDatabaseIsAvailable() {
        val treeModelStructure = TreeStructureModel(
            sha = "unique_string",
            tree = listOf(
                Tree(
                    mode = "100644",
                    path = ".CHANGELOG",
                    sha = "unique_string",
                    size = 100,
                    type = RepositoryTypeModel.BLOB,
                    url = "test_url"
                )
            ),
            truncated = false,
            url = "test_url"
        )

        every { networkHelper.isNetworkConnected.value } returns true
        coEvery { githubService.getTreeStructure(name, repo) } returns treeModelStructure
        coEvery { githubTreeItemDao.saveTreeList(any()) } returns Unit

        val treeDomainItemList = GithubRepositoryTreeDomainItem(
            path = ".CHANGELOG",
            sha = "unique_string",
            type = RepositoryTypeDomain.BLOB
        )

        coroutinesTestRule.runBlockingTest {
            githubRepositoryDetailed.getGithubRepositoryTrees(name, repo).test {
                assertThat(treeDomainItemList).isEqualTo(expectItem().first())
                expectComplete()
            }
        }
    }

    @Test
    fun testRepositoryWhenDatabaseIsNotAvailable() {
        val treeItemEntity = TreeItemEntity(
            fullName = getFullRepositoryName(name, repo),
            path = ".CHANGELOG",
            sha = "unique_string",
            type = RepositoryTypeDomain.BLOB
        )

        every { networkHelper.isNetworkConnected.value } returns false
        coEvery { githubTreeItemDao.getTreeListByNameAndRepo(any()) } returns flow { emit(listOf(treeItemEntity)) }
        coEvery { networkHelper.isNetworkConnected } returns MutableStateFlow(false)

        val treeDomainItemList = GithubRepositoryTreeDomainItem(
            path = ".CHANGELOG",
            sha = "unique_string",
            type = RepositoryTypeDomain.BLOB,
        )

        coroutinesTestRule.runBlockingTest {
            githubRepositoryDetailed.getGithubRepositoryTrees(name, repo).test {
                assertThat(treeDomainItemList).isEqualTo(expectItem().first())
            }
        }
    }
}