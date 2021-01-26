package br.com.israelermel.feature_top_ranked.states

import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.models.repositories.RepositoriesBo

sealed class GitHubRepositoriesState {
    object Loading : GitHubRepositoriesState()
    data class Success(val repositories: PagingData<ReposEntity>) : GitHubRepositoriesState()
    data class Error(val error: RepositoriesException) : GitHubRepositoriesState()

}