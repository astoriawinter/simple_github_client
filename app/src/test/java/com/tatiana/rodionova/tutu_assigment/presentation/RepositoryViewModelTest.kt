package com.tatiana.rodionova.tutu_assigment.presentation

import app.cash.turbine.test
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryListUseCase
import com.tatiana.rodionova.presentation.github_list.RepositoryListViewModel
import com.tatiana.rodionova.tutu_assigment.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class RepositoryViewModelTest {
    private val fetchGithubRepositoryListUseCase = mockk<FetchGithubRepositoryListUseCase>()
    private lateinit var repositoryListViewModel: RepositoryListViewModel

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()

    @Test
    fun testViewModelDataLoadedState() {
        val data = listOf(
            GithubRepositoryListDomainItem(
                name = "test",
                description = null,
                language = null,
                stargazers_count = 10,
                updated_at = "2021-09-02T08:05:23.653Z"
            )
        )
        coEvery { fetchGithubRepositoryListUseCase.invoke() } returns flow { emit(data) }
        repositoryListViewModel = RepositoryListViewModel(
            coroutinesTestRule.testCoroutineDispatcher,
            fetchGithubRepositoryListUseCase
        )

        coroutinesTestRule.runBlockingTest {
            repositoryListViewModel.state.test {
                assertEquals(expectItem(), RepositoryListViewModel.State.DataLoaded(data))
            }
        }
    }

    @Test
    fun testViewModelLoadingState() {
        coEvery { fetchGithubRepositoryListUseCase.invoke() } returns flow {}
        repositoryListViewModel = RepositoryListViewModel(
            coroutinesTestRule.testCoroutineDispatcher,
            fetchGithubRepositoryListUseCase
        )

        coroutinesTestRule.runBlockingTest {
            assertEquals(
                repositoryListViewModel.state.first(),
                RepositoryListViewModel.State.Loading
            )
        }
    }

    @Test
    fun testViewModelErrorState() {
        val testError = Throwable("test error")
        coEvery { fetchGithubRepositoryListUseCase.invoke() } throws testError

        repositoryListViewModel = RepositoryListViewModel(
            coroutinesTestRule.testCoroutineDispatcher,
            fetchGithubRepositoryListUseCase
        )

        coroutinesTestRule.runBlockingTest {
            repositoryListViewModel.state.test {
                assertEquals(expectItem(), RepositoryListViewModel.State.Error(testError))
            }
        }
    }
}