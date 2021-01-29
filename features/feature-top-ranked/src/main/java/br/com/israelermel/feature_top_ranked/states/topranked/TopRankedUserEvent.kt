package br.com.israelermel.feature_top_ranked.states.topranked

sealed class TopRankedUserEvent {
    object GetTopRankedUserEvent : TopRankedUserEvent()
}