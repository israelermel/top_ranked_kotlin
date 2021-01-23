package br.com.israelermel.domain.states

sealed class LoadingState {
    object Loading : LoadingState()
    object UnLoad : LoadingState()
}