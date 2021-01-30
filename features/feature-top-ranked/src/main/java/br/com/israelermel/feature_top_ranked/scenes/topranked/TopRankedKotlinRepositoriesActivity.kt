package br.com.israelermel.feature_top_ranked.scenes.topranked

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.feature_top_ranked.adapters.repos.ReposAdapter
import br.com.israelermel.feature_top_ranked.adapters.repos.ReposLoadStateAdapter
import br.com.israelermel.feature_top_ranked.databinding.TopRankedKotlinRepositoriesBinding
import br.com.israelermel.feature_top_ranked.states.topranked.ReposResultState
import br.com.israelermel.feature_top_ranked.states.topranked.TopRankedUserEvent
import br.com.israelermel.feature_top_ranked.states.topranked.toStateResource
import br.com.israelermel.library_arch.configSimpleVerticalDividerDecoration
import br.com.israelermel.library_arch.extensions.setVisibilityState
import br.com.israelermel.library_arch.viewstates.VisibilityState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.getViewModel

class TopRankedKotlinRepositoriesActivity : AppCompatActivity() {

    lateinit var binding: TopRankedKotlinRepositoriesBinding
    private lateinit var viewModel: TopRankedKotlinRepositoriesViewModel
    private val adapter = ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TopRankedKotlinRepositoriesBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@TopRankedKotlinRepositoriesActivity
            setContentView(root)
        }

        setupViewModel()
        initAdapter()

        setupUserActions()
        attachObservers()

    }

    private fun setupViewModel() {
        viewModel = getViewModel()
        binding.viewModel = viewModel
    }

    private fun initAdapter() {
        configRetryButtons()

        binding.repoTopRankedList.configSimpleVerticalDividerDecoration()
    }

    private fun configRetryButtons() {
        binding.repoTopRankedList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapter.retry() },
            footer = ReposLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            progressBarState(loadState)
            recyclerViewState(loadState)
        }
    }

    private fun setupUserActions() {
        actionOnEvent(
            TopRankedUserEvent.GetTopRankedUserEvent
        )

    }

    private fun actionOnEvent(eventUser: TopRankedUserEvent) {
        when (eventUser) {
            is TopRankedUserEvent.GetTopRankedUserEvent -> {
                viewModel.getGitHubRepositories()
            }
        }
    }

    private fun updateProjects(repositories: PagingData<ReposEntity>) {
        lifecycleScope.launch {
            repositories.let {
                adapter.submitData(it)
            }
        }
    }

    private fun attachObservers() {
        with(viewModel) {
            resultResultState.observe(this@TopRankedKotlinRepositoriesActivity, Observer { response ->
                when (response) {
                    is ReposResultState.Success -> {
                        updateProjects(response.repositories)
                    }

                    is ReposResultState.Error -> {
                        showErrorState(response.toStateResource().message)
                    }
                }
            })
        }


        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.repoTopRankedList.scrollToPosition(0) }
        }
    }

    private fun showErrorState(msg: Int) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(msg)
            .setCancelable(true)
            .setPositiveButton("Ok") { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

    private fun recyclerViewState(loadState: CombinedLoadStates) {
        binding.repoTopRankedList.setVisibilityState(
            if (loadState.source.refresh is LoadState.NotLoading) {
                VisibilityState.VISIBLE
            } else {
                VisibilityState.GONE
            }
        )
    }

    private fun progressBarState(loadState: CombinedLoadStates) {
        binding.topRankedProgressBar.setVisibilityState(
            if (loadState.source.refresh is LoadState.Loading) {
                VisibilityState.VISIBLE
            } else {
                VisibilityState.GONE
            }
        )
    }
}
