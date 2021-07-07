package com.tatiana.rodionova.presentation.github_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryListViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val fetchGithubRepositoryListUseCase: FetchGithubRepositoryListUseCase
) : ViewModel() {
    private val _state = MutableSharedFlow<State>(replay = 1)
    val state: SharedFlow<State> = _state

    sealed interface State {
        object Loading : State
        data class Error(val error: Throwable) : State
        data class DataLoaded(val githubItemList: List<GithubRepositoryListDomainItem>) : State
    }

    fun loadRepositoryList() {
        viewModelScope.launch(dispatcher) {
            _state.emit(State.Loading)

            try {
                fetchGithubRepositoryListUseCase.invoke().collect { githubItemList ->
                    _state.emit(State.DataLoaded(githubItemList))
                }
            } catch (e: Throwable) {
                _state.emit(State.Error(e))
            }
        }
    }

    init {
        loadRepositoryList()
    }
}