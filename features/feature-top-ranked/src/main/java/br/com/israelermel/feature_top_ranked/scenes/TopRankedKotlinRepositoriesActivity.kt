package br.com.israelermel.feature_top_ranked.scenes

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesKeyParam
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.states.LoadingState
import br.com.israelermel.feature_top_ranked.adapters.ReposAdapter
import br.com.israelermel.feature_top_ranked.adapters.ReposLoadStateAdapter
import br.com.israelermel.feature_top_ranked.adapters.setVisible
import br.com.israelermel.feature_top_ranked.databinding.TopRankedKotlinRepositoriesBinding
import br.com.israelermel.feature_top_ranked.states.GitHubRepositoriesState
import br.com.israelermel.feature_top_ranked.states.TopRankedUserEvent
import br.com.israelermel.feature_top_ranked.states.toStateResource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.android.viewmodel.ext.android.getViewModel

class TopRankedKotlinRepositoriesActivity : AppCompatActivity() {

    lateinit var binding: TopRankedKotlinRepositoriesBinding
    private lateinit var viewModel: TopRankedKotlinRepositoriesViewModel
    private val adapter = ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TopRankedKotlinRepositoriesBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setupViewModel()
        initAdapter()

        setupUserActions()
        attachObservers()

        binding.retryButton.setOnClickListener { adapter.retry() }
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
    }

    private fun setupViewModel() {
        viewModel = getViewModel()
        binding.viewModel = viewModel
    }

    private fun initAdapter() {
        configRetryButtons()

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)
    }

    private fun configRetryButtons() {
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapter.retry() },
            footer = ReposLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.list.setVisible(loadState.source.refresh is LoadState.NotLoading)
            // Show loading spinner during initial load or refresh.
            binding.progressBar.setVisible(loadState.source.refresh is LoadState.Loading)
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.setVisible(loadState.source.refresh is LoadState.Error)

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupUserActions() {
        val request = executeGetTopRankedRepositories()
        actionOnEvent(
            TopRankedUserEvent.GetTopRankedUserEvent(request)
        )
    }

    private fun executeGetTopRankedRepositories(): GitHubRepositoriesRequest {
        val params = mutableMapOf<String, String>().apply {
            put(GitHubRepositoriesKeyParam.FILTER.value, "language:kotlin")
            put(GitHubRepositoriesKeyParam.SORT.value, "stars")
            put(GitHubRepositoriesKeyParam.PAGE.value, "1")
            put(GitHubRepositoriesKeyParam.PER_PAGE.value, "50")
        }

        return GitHubRepositoriesRequest(
            params = params
        )
    }

    private fun actionOnEvent(eventUser: TopRankedUserEvent) {
        when (eventUser) {
            is TopRankedUserEvent.GetTopRankedUserEvent -> {
                renderLoading(LoadingState.Loading)
                viewModel.getGitHubRepositories(eventUser.getTopRankedProjects)
            }
        }
    }

    private fun updateProjects(repositories: PagingData<ReposEntity>) {
        MainScope().launch {
            repositories.let {
                adapter.submitData(it)
            }
        }

        Toast.makeText(
            this@TopRankedKotlinRepositoriesActivity, "Salvo com sucesso",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun attachObservers() {
        with(viewModel) {
            resultState.observe(this@TopRankedKotlinRepositoriesActivity, Observer { response ->
                when (response) {
                    is GitHubRepositoriesState.Loading -> showLoadingState()
                    is GitHubRepositoriesState.Success -> {
                        renderLoading(LoadingState.UnLoad)

                        updateProjects(response.repositories)
                    }

                    is GitHubRepositoriesState.Error -> {
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
                .collect { binding.list.scrollToPosition(0) }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "1"
    }
}
