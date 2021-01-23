package br.com.israelermel.feature_top_ranked.states

import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest

sealed class TopRankedUserEvent {
    data class GetTopRankedUserEvent(val getTopRankedProjects: GitHubRepositoriesRequest) : TopRankedUserEvent()
}