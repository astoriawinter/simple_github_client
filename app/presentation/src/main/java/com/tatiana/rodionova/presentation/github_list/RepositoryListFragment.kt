package com.tatiana.rodionova.presentation.github_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.presentation.R
import com.tatiana.rodionova.presentation.databinding.FragmentRepositoryListBinding
import com.tatiana.rodionova.presentation.github_list.adapter.RepositoryListAdapter
import com.tatiana.rodionova.presentation.github_list.adapter.decorator.OffsetDividerItemDecoration
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RepositoryListFragment : DaggerAppCompatDialogFragment() {

    private var viewBinding: FragmentRepositoryListBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<RepositoryListViewModel> { viewModelFactory }
    private var repositoryListAdapter: RepositoryListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        viewBinding?.swipeRefresh?.setOnRefreshListener {
            viewModel.loadRepositoryList()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is RepositoryListViewModel.State.Loading -> {
                        renderLoadingState()
                    }
                    is RepositoryListViewModel.State.DataLoaded -> {
                        renderLoadedData(state.githubItemList)
                    }
                    is RepositoryListViewModel.State.Error -> {
                        renderErrorState(state.error)
                    }
                }
            }
        }
    }

    private fun renderLoadingState() {
        viewBinding?.run {
            if (!swipeRefresh.isRefreshing) {
                loader.visibility = View.VISIBLE
            }
        }
    }

    private fun hideLoaders() {
        viewBinding?.run {
            loader.visibility = View.GONE
            swipeRefresh.isRefreshing = false
        }
    }

    private fun renderLoadedData(githubItemList: List<GithubRepositoryListDomainItem>) {
        hideLoaders()
        repositoryListAdapter?.githubItemList = githubItemList
    }

    private fun renderErrorState(error: Throwable) {
        hideLoaders()
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
    }

    private fun openDetailedScreen(item: GithubRepositoryListDomainItem) {
        val action = RepositoryListFragmentDirections.goToDetailedScreen(item)
        findNavController(this).navigate(action)
    }

    private fun initRecyclerView() {
        viewBinding?.repositories?.run {
            layoutManager = LinearLayoutManager(context)
            RepositoryListAdapter(::openDetailedScreen).run {
                adapter = this
                addItemDecoration(
                    OffsetDividerItemDecoration(
                        requireContext(),
                        resources.getDimensionPixelOffset(R.dimen.offset)
                    )
                )
                repositoryListAdapter = this
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}