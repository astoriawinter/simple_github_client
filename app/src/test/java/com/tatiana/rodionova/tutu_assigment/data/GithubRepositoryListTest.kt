package com.tatiana.rodionova.tutu_assigment.data

import app.cash.turbine.test
import com.tatiana.rodionova.data.api.GithubService
import com.tatiana.rodionova.data.api.helper.NetworkHelper
import com.tatiana.rodionova.data.database.dao.GithubItemDao
import com.tatiana.rodionova.data.model.dto.GithubItem
import com.tatiana.rodionova.data.model.dto.GithubRepositoryModel
import com.tatiana.rodionova.data.model.entity.GithubItemEntity
import com.tatiana.rodionova.data.repository.GithubRepositoryListImplementation
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
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
class GithubRepositoryListTest {
    private val key = "android"
    private val sortBy = "star"
    private val githubService = mockk<GithubService>()
    private val githubItemDao = mockk<GithubItemDao>()
    private val networkHelper = mockk<NetworkHelper>()
    private val githubItem = GithubRepositoryListDomainItem(
        description = "sample",
        name = "android/tutu",
        language = "kotlin",
        updated_at = "2021-09-02T08:05:23.653Z",
        stargazers_count = 100
    )

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()

    private val githubRepositoryList = spyk(
        GithubRepositoryListImplementation(
            coroutinesTestRule.testCoroutineDispatcher,
            key,
            sortBy,
            githubService,
            githubItemDao,
            networkHelper
        )
    )

    @Test
    fun testRepositoryWhenDatabaseIsAvailable() {
        val githubRepositoryModel = GithubRepositoryModel(
            incomplete_results = false,
            items = listOf(
                GithubItem(
                    id = 121,
                    description = "sample",
                    full_name = "android/tutu",
                    language = "kotlin",
                    pushed_at = "2021-09-02T08:05:23.653Z",
                    stargazers_count = 100
                )
            ),
            total_count = 500
        )

        every { networkHelper.isNetworkConnected.value } returns true
        coEvery { githubService.getRepositories(key, sortBy) } returns githubRepositoryModel
        coEvery { githubItemDao.saveGithubItemList(any()) } returns Unit

        coroutinesTestRule.runBlockingTest {
            githubRepositoryList.getGithubAndroidRepositories().test {
                assertThat(githubItem).isEqualTo(expectItem().first())
                expectComplete()
            }
        }
    }

    @Test
    fun testRepositoryWhenDatabaseIsNotAvailable() {
        val githubItemEntity = GithubItemEntity(
            id = 100500,
            description = "sample",
            name = "android/tutu",
            language = "kotlin",
            updated_at = "2021-09-02T08:05:23.653Z",
            stargazers_count = 100
        )

        every { networkHelper.isNetworkConnected.value } returns false
        coEvery { githubItemDao.getGithubItemList() } returns flow { emit(listOf(githubItemEntity)) }
        coEvery { networkHelper.isNetworkConnected } returns MutableStateFlow(false)

        coroutinesTestRule.runBlockingTest {
            githubRepositoryList.getGithubAndroidRepositories().test {
                assertThat(githubItem).isEqualTo(expectItem().first())
            }
        }
    }
}