package com.tatiana.rodionova.presentation.github_detailed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.presentation.databinding.FragmentRepositoryDetailedBinding
import com.tatiana.rodionova.presentation.github_detailed.adapter.RepositoryDetailedAdapter
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RepositoryDetailedFragment : DaggerAppCompatDialogFragment() {

    private var viewBinding: FragmentRepositoryDetailedBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<RepositoryDetailedViewModel> { viewModelFactory }

    private var repositoryDetailedAdapter: RepositoryDetailedAdapter? = null
    private val item: GithubRepositoryListDomainItem? by lazy {
        arguments?.getParcelable(
            ARGUMENT_ITEM
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRepositoryDetailedBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        item?.let(::renderDetailedData)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            item?.name?.let { fullName ->
                viewModel.getRepositoryDetails(fullName)

                viewModel.state.collect { state ->
                    when (state) {
                        is RepositoryDetailedViewModel.State.Loading -> {
                            renderLoadingState()
                        }
                        is RepositoryDetailedViewModel.State.TreeLoaded -> {
                            renderTreeLoadedData(state.treeData)
                        }
                        is RepositoryDetailedViewModel.State.Error -> {
                            renderErrorState(state.error)
                        }
                    }
                }
            }
        }
    }

    private fun renderLoadingState() {
        viewBinding?.loader?.visibility = View.VISIBLE
    }

    private fun renderDetailedData(
        githubRepositoryDetailedDomainItem: GithubRepositoryListDomainItem
    ) {
        viewBinding?.run {
            repositoryName.text = githubRepositoryDetailedDomainItem.name
        }
    }

    private fun renderTreeLoadedData(treeData: List<GithubRepositoryTreeDomainItem>) {
        repositoryDetailedAdapter?.treeList = treeData
        viewBinding?.loader?.visibility = View.GONE
    }

    private fun renderErrorState(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
        viewBinding?.loader?.visibility = View.GONE
    }

    private fun initRecyclerView() {
        viewBinding?.repositories?.run {
            layoutManager = LinearLayoutManager(context)
            RepositoryDetailedAdapter().run {
                adapter = this
                repositoryDetailedAdapter = this
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        const val ARGUMENT_ITEM = "item"
    }
}