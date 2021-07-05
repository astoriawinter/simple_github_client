package com.tatiana.rodionova.presentation.github_detailed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.model.getNameAndRepository
import com.tatiana.rodionova.domain.usecase.FetchGithubRepositoryTreeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryDetailedViewModel @Inject constructor(
    private val fetchGithubRepositoryTreeUseCase: FetchGithubRepositoryTreeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    sealed interface State {
        object Loading : State
        data class Error(val error: Throwable) : State
        data class TreeLoaded(val treeData: List<GithubRepositoryTreeDomainItem>) : State
    }

    fun getRepositoryDetails(fullName: String) =
        viewModelScope.launch {
            val (name, repo) = fullName.getNameAndRepository()

            loadTreeData(name, repo)
        }

    private suspend fun loadTreeData(name: String, repo: String) =
        try {
            fetchGithubRepositoryTreeUseCase.invoke(name, repo).collect { tree ->
                _state.value = State.TreeLoaded(tree)
            }
        } catch (e: Throwable) {
            _state.value = State.Error(e)
        }
}