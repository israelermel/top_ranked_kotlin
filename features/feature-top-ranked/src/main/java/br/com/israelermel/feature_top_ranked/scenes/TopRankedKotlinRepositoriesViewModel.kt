package br.com.israelermel.feature_top_ranked.scenes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCase
import br.com.israelermel.feature_top_ranked.states.ReposResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopRankedKotlinRepositoriesViewModel(
    private val getReposLanguageKotlinUseCase: GetReposLanguageKotlinUseCase
) : ViewModel() {

    private val _resultState = MutableLiveData<ReposResultState>()

    val resultResultState: LiveData<ReposResultState>
        get() = _resultState


    fun getGitHubRepositories() {
        viewModelScope.launch {

            when (val repositories = getReposLanguageKotlinUseCase.execute()) {
                is RequestResult.Success -> {

                    repositories.result.cachedIn(viewModelScope).collectLatest {
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