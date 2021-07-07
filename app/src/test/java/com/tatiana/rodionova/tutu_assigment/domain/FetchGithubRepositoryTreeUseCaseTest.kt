package com.tatiana.rodionova.tutu_assigment.domain

import app.cash.turbine.test
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.model.RepositoryTypeDomain
import com.tatiana.rodionova.domain.repository.GithubDetailedRepository
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryTreeUseCase
import com.tatiana.rodionova.tutu_assigment.TestCoroutineRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class FetchGithubRepositoryTreeUseCaseTest {
    private lateinit var fetchGithubRepositoryListUseCaseTest: FetchGithubRepositoryTreeUseCase
    private val githubRepository = mockk<GithubDetailedRepository>()
    private val name = "android"
    private val repo = "testRepo"

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()
    private val data = listOf(
        GithubRepositoryTreeDomainItem(
            path = ".CHANGELOG",
            type = RepositoryTypeDomain.BLOB,
            sha = "random_unique_string"
        )
    )

    @Before
    fun setup() {
        fetchGithubRepositoryListUseCaseTest = FetchGithubRepositoryTreeUseCase(githubRepository)
        every { githubRepository.getGithubRepositoryTrees(name, repo) } returns flow { emit(data) }
    }

    @Test
    fun testGettingGithubAndroidRepositories() {
        coroutinesTestRule.runBlockingTest {
            fetchGithubRepositoryListUseCaseTest.invoke(name, repo).test {
                assertEquals(expectItem(), data)
                expectComplete()
            }
        }
    }
}