package br.com.israelermel.feature_top_ranked.states

import br.com.israelermel.domain.exceptions.RepositoriesException

sealed class GitHubRepositoriesState {
    object Loading : GitHubRepositoriesState()
    object Success : GitHubRepositoriesState()
    data class Error(val error: RepositoriesException) : GitHubRepositoriesState()

}