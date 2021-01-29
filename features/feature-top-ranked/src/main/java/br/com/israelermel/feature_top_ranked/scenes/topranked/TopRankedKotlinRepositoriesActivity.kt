package br.com.israelermel.feature_top_ranked.scenes.topranked

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.states.LoadingState
import br.com.israelermel.feature_top_ranked.adapters.repos.ReposAdapter
import br.com.israelermel.feature_top_ranked.adapters.repos.ReposLoadStateAdapter
import br.com.israelermel.feature_top_ranked.databinding.TopRankedKotlinRepositoriesBinding
import br.com.israelermel.feature_top_ranked.states.topranked.ReposResultState
import br.com.israelermel.feature_top_ranked.states.topranked.TopRankedUserEvent
import br.com.israelermel.feature_top_ranked.states.topranked.toStateResource
import br.com.israelermel.library_arch.extensions.setVisibilityState
import br.com.israelermel.library_arch.viewstates.VisibilityState
import kotlinx.coroutines.MainScope
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

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.repoTopRankedList.addItemDecoration(decoration)
    }

    private fun configRetryButtons() {
        binding.repoTopRankedList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapter.retry() },
            footer = ReposLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->

            if (loadState.source.refresh is LoadState.NotLoading) {
                binding.repoTopRankedList.setVisibilityState(VisibilityState.VISIBLE)
            } else {
                binding.repoTopRankedList.setVisibilityState(VisibilityState.GONE)
            }

            if (loadState.source.refresh is LoadState.Loading) {
                binding.progressBar.setVisibilityState(VisibilityState.VISIBLE)
            } else {
                binding.progressBar.setVisibilityState(VisibilityState.GONE)
            }

            if (loadState.source.refresh is LoadState.Error) {
                binding.retryButton.setVisibilityState(VisibilityState.VISIBLE)
            } else {
                binding.retryButton.setVisibilityState(VisibilityState.GONE)
            }

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                    this,
                    "Falha: ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupUserActions() {
        actionOnEvent(
            TopRankedUserEvent.GetTopRankedUserEvent
        )

        binding.retryButton.setOnClickListener { adapter.retry() }
    }

    private fun actionOnEvent(eventUser: TopRankedUserEvent) {
        when (eventUser) {
            is TopRankedUserEvent.GetTopRankedUserEvent -> {
                renderLoading(LoadingState.Loading)
                viewModel.getGitHubRepositories()
            }
        }
    }

    private fun updateProjects(repositories: PagingData<ReposEntity>) {
        MainScope().launch {
            repositories.let {
                adapter.submitData(it)
            }
        }
    }

    private fun attachObservers() {
        with(viewModel) {
            resultResultState.observe(this@TopRankedKotlinRepositoriesActivity, Observer { response ->
                when (response) {
                    is ReposResultState.Loading -> showLoadingState()
                    is ReposResultState.Success -> {
                        renderLoading(LoadingState.UnLoad)

                        updateProjects(response.repositories)
                    }

                    is ReposResultState.Error -> {
                        renderLoading(LoadingState.UnLoad)

                        showErrorState(response.toStateResource().message)
                    }
                }
            })
        }


        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
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

    private fun showLoadingState() {
        binding.loginProgressBar.show()
    }

    private fun hideLoadingState() {
        binding.loginProgressBar.hide()
    }

    fun renderLoading(loadingState: LoadingState) {
        when (loadingState) {
            is LoadingState.Loading -> showLoadingState()
            is LoadingState.UnLoad -> hideLoadingState()
        }
    }

}
