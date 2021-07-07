package com.tatiana.rodionova.tutu_assigment.domain

import app.cash.turbine.test
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.repository.GithubListRepository
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryListUseCase
import com.tatiana.rodionova.tutu_assigment.TestCoroutineRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class FetchGithubRepositoryListUseCaseTest {
    private lateinit var fetchGithubRepositoryListUseCaseTest: FetchGithubRepositoryListUseCase
    private val githubRepository = mockk<GithubListRepository>()

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()
    private val data = listOf(
        GithubRepositoryListDomainItem(
            name = "test",
            description = null,
            language = null,
            stargazers_count = 10,
            updated_at = "2021-09-02T08:05:23.653Z"
        )
    )

    @Before
    fun setup() {
        fetchGithubRepositoryListUseCaseTest = FetchGithubRepositoryListUseCase(githubRepository)
        every { githubRepository.getGithubAndroidRepositories() } returns flow { emit(data) }
    }

    @Test
    fun testGettingGithubAndroidRepositories() {
        coroutinesTestRule.runBlockingTest {
            fetchGithubRepositoryListUseCaseTest.invoke().test {
                assertEquals(expectItem(), data)
                expectComplete()
            }
        }
    }
}