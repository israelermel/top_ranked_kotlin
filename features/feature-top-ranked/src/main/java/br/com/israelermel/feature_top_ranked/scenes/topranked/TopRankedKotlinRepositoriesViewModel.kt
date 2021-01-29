package br.com.israelermel.feature_top_ranked.scenes.topranked

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCase
import br.com.israelermel.feature_top_ranked.states.topranked.ReposResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopRankedKotlinRepositoriesViewModel(
    private val getReposLanguageKotlinUseCaseImpl: GetReposLanguageKotlinUseCase
) : ViewModel() {

    private val _resultState = MutableLiveData<ReposResultState>()

    val resultResultState: LiveData<ReposResultState>
        get() = _resultState


    fun getGitHubRepositories() {
        viewModelScope.launch {

            when (val repositories = getReposLanguageKotlinUseCaseImpl.execute()) {
                is RequestResult.Success -> {
                    repositories.result.collectLatest {
                        _resultState.postValue(ReposResultState.Success(it))
                    }
                }

                is RequestResult.Failure -> {
                    _resultState.postValue(
                        ReposResultState.Error(
                            error = repositories.throwable as RepositoriesException
                        )
                    )
                }
            }
        }
    }

}