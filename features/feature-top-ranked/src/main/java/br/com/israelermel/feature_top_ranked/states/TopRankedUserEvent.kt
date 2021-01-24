package br.com.israelermel.feature_top_ranked.states

import br.com.israelermel.domain.models.repositories.BestProjectsKotlinRequest

sealed class TopRankedUserEvent {
    data class GetTopRankedUserEvent(val getTopRankedProjects: BestProjectsKotlinRequest) : TopRankedUserEvent()
}