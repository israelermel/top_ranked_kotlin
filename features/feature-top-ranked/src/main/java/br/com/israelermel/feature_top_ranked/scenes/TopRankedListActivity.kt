package br.com.israelermel.feature_top_ranked.scenes

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesParamsEnum
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.states.LoadingState
import br.com.israelermel.feature_top_ranked.databinding.TopRankedBinding
import br.com.israelermel.feature_top_ranked.states.GitHubRepositoriesState
import br.com.israelermel.feature_top_ranked.states.TopRankedUserEvent
import br.com.israelermel.feature_top_ranked.states.toStateResource
import org.koin.android.viewmodel.ext.android.getViewModel

class TopRankedListActivity : AppCompatActivity() {

    lateinit var binding: TopRankedBinding
    private lateinit var viewModel: TopRankedListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TopRankedBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setupViewModel()
        setupUserActions()
        attachObservers()
    }

    private fun setupViewModel() {
        viewModel = getViewModel()
        binding.viewModel = viewModel
    }

    private fun setupUserActions() {
        with(binding) {
            btnSearchTopRankedRepositories.setOnClickListener {
                val request = executeGetTopRankedRepositories()

                actionOnEvent(
                    TopRankedUserEvent.GetTopRankedUserEvent(request)
                )

            }
        }
    }

    private fun TopRankedBinding.executeGetTopRankedRepositories(): GitHubRepositoriesRequest {
        val params = mutableMapOf<String, String>().apply {
            put(GitHubRepositoriesParamsEnum.FILTER.value, "language:kotlin")
            put(GitHubRepositoriesParamsEnum.SORT.value, "stargazers")
            put(GitHubRepositoriesParamsEnum.PAGE.value, "1")
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
            resultState.observe(this@TopRankedListActivity, Observer { response ->
                when(response) {
                    is GitHubRepositoriesState.Loading -> showLoadingState()
                    is GitHubRepositoriesState.Success -> {
                        renderLoading(LoadingState.UnLoad)

                        Toast.makeText(
                            this@TopRankedListActivity, "Salvo com sucesso",
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
