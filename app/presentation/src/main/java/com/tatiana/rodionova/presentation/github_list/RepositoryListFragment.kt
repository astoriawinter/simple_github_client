package com.tatiana.rodionova.presentation.github_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatiana.rodionova.data.model.GithubItem
import com.tatiana.rodionova.domain.model.GithubRepositoryDomainItem
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
        viewBinding?.loader?.visibility = View.VISIBLE
    }

    private fun renderLoadedData(githubItemList: List<GithubRepositoryDomainItem>) {
        viewBinding?.loader?.visibility = View.GONE
        repositoryListAdapter?.githubItemList = githubItemList
    }

    private fun renderErrorState(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
        viewBinding?.loader?.visibility = View.GONE
    }

    private fun initRecyclerView() {
        viewBinding?.repositories?.run {
            layoutManager = LinearLayoutManager(context)
            RepositoryListAdapter().run {
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