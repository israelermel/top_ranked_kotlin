package br.com.israelermel.feature_top_ranked.states

sealed class TopRankedUserEvent {
    object GetTopRankedUserEvent : TopRankedUserEvent()
}