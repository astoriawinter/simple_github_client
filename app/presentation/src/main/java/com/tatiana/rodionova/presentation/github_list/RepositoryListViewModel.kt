package com.tatiana.rodionova.presentation.github_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatiana.rodionova.domain.model.GithubRepositoryDomainItem
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryListViewModel @Inject constructor(
    private val fetchGithubRepositoryListUseCase: FetchGithubRepositoryListUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    sealed interface State {
        object Loading : State
        data class Error(val error: Throwable) : State
        data class DataLoaded(val githubItemList: List<GithubRepositoryDomainItem>) : State
    }

    init {
        viewModelScope.launch {
            try {
                fetchGithubRepositoryListUseCase.invoke().collect { githubItemList ->
                    _state.value = State.DataLoaded(githubItemList)
                }
            } catch (e: Throwable) {
                _state.value = State.Error(e)
            }
        }
    }
}