package br.com.israelermel.feature_top_ranked.scenes

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesKeyParam
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.states.LoadingState
import br.com.israelermel.feature_top_ranked.adapters.ReposAdapter
import br.com.israelermel.feature_top_ranked.databinding.TopRankedKotlinRepositoriesBinding
import br.com.israelermel.feature_top_ranked.states.GitHubRepositoriesState
import br.com.israelermel.feature_top_ranked.states.TopRankedUserEvent
import br.com.israelermel.feature_top_ranked.states.toStateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        setupUserActions()
        attachObservers()
    }

    private fun setupViewModel() {
        viewModel = getViewModel()
        binding.viewModel = viewModel
        binding.list.adapter = adapter
    }

    private fun setupUserActions() {
        val request = executeGetTopRankedRepositories()
        actionOnEvent(
            TopRankedUserEvent.GetTopRankedUserEvent(request)
        )


//        with(binding) {
//            btnSearchTopRankedRepositories.setOnClickListener {
//                val request = executeGetTopRankedRepositories()
//
//                actionOnEvent(
//                    TopRankedUserEvent.GetTopRankedUserEvent(request)
//                )
//
//            }
//        }
    }

    private fun executeGetTopRankedRepositories(): GitHubRepositoriesRequest {
        val params = mutableMapOf<String, String>().apply {
            put(GitHubRepositoriesKeyParam.FILTER.value, "language:kotlin")
            put(GitHubRepositoriesKeyParam.SORT.value, "stargazers")
            put(GitHubRepositoriesKeyParam.PAGE.value, "1")
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

    private fun attachObservers() {
        with(viewModel) {
            resultState.observe(this@TopRankedKotlinRepositoriesActivity, Observer { response ->
                when (response) {
                    is GitHubRepositoriesState.Loading -> showLoadingState()
                    is GitHubRepositoriesState.Success -> {
                        renderLoading(LoadingState.UnLoad)

                        MainScope().launch {
                            response.repositories.let {
                                adapter.submitData(it)
                            }
                        }

                        Toast.makeText(
                            this@TopRankedKotlinRepositoriesActivity, "Salvo com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
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
}
