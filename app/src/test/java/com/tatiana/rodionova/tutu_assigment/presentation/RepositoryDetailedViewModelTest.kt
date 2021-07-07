package com.tatiana.rodionova.tutu_assigment.presentation

import app.cash.turbine.test
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.model.RepositoryTypeDomain
import com.tatiana.rodionova.domain.model.getFullRepositoryName
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryTreeUseCase
import com.tatiana.rodionova.presentation.github_detailed.RepositoryDetailedViewModel
import com.tatiana.rodionova.tutu_assigment.TestCoroutineRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class RepositoryDetailedViewModelTest {
    private val fetchGithubRepositoryTreeUseCase = mockk<FetchGithubRepositoryTreeUseCase>()
    private lateinit var repositoryDetailedViewModel: RepositoryDetailedViewModel

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()
    private val name = "android"
    private val repo = "testRepo"

    @Before
    fun setup() {
        repositoryDetailedViewModel = RepositoryDetailedViewModel(
            coroutinesTestRule.testCoroutineDispatcher,
            fetchGithubRepositoryTreeUseCase
        )
    }

    @Test
    fun testViewModelDataLoadedState() {
        val data = listOf(
            GithubRepositoryTreeDomainItem(
                path = ".CHANGELOG",
                type = RepositoryTypeDomain.BLOB,
                sha = "random_unique_string"
            )
        )
        every { fetchGithubRepositoryTreeUseCase.invoke(name, repo) } returns flow {
            emit(data)
        }
        repositoryDetailedViewModel.getRepositoryDetails(getFullRepositoryName(name, repo))

        coroutinesTestRule.runBlockingTest {
            repositoryDetailedViewModel.state.test {
                assertEquals(expectItem(), RepositoryDetailedViewModel.State.TreeLoaded(data))
            }
        }
    }

    @Test
    fun testViewModelLoadingState() {
        every { fetchGithubRepositoryTreeUseCase.invoke(name, repo) } returns flow {}
        coroutinesTestRule.runBlockingTest {
            assertEquals(
                repositoryDetailedViewModel.state.first(),
                RepositoryDetailedViewModel.State.Loading
            )
        }
    }

    @Test
    fun testViewModelErrorState() {
        val testError = Throwable("test error")
        every { fetchGithubRepositoryTreeUseCase.invoke(name, repo) } throws testError

        repositoryDetailedViewModel.getRepositoryDetails(getFullRepositoryName(name, repo))

        coroutinesTestRule.runBlockingTest {
            repositoryDetailedViewModel.state.test {
                assertEquals(expectItem(), RepositoryDetailedViewModel.State.Error(testError))
            }
        }
    }
}