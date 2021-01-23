package br.com.israelermel.feature_top_ranked.scenes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetGithubRepositoriesUseCase
import br.com.israelermel.feature_top_ranked.states.GitHubRepositoriesState
import kotlinx.coroutines.launch

class TopRankedListViewModel(
    private val getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase
) : ViewModel() {

    private val _resultState = MutableLiveData<GitHubRepositoriesState>()

    val resultState: LiveData<GitHubRepositoriesState>
        get() = _resultState


    fun getGitHubRepositories(resquest: GitHubRepositoriesRequest) {
        viewModelScope.launch {

            when(val repositories = getGithubRepositoriesUseCase.execute(resquest)) {
                is RequestResult.Success -> {
                    _resultState.postValue(GitHubRepositoriesState.Success(repositories.result))
                }

                is RequestResult.Failure -> {
                    _resultState.postValue(
                        GitHubRepositoriesState.Error(
                            error = repositories.throwable as RepositoriesException
                        )
                    )
                }
            }

        }
    }

}