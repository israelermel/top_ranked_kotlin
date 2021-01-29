package br.com.israelermel.feature_top_ranked.states.topranked

import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.feature_top_ranked.R

data class TopRankedUserErrorStateResources(val message: Int) {
    companion object {
        operator fun invoke(error: RepositoriesException) =
            when(error) {
                is RepositoriesException.RequestRepositoriesException -> TopRankedUserErrorStateResources(
                    R.string.top_ranked_request_error
                )

                is RepositoriesException.UnknownRepositoriesException -> TopRankedUserErrorStateResources(
                    R.string.top_ranked_unkown_error
                )

                is RepositoriesException.EmptyRepositoriesBodyException -> TopRankedUserErrorStateResources(
                    R.string.top_ranked_empty_body_response_error
                )
            }
    }
}

fun ReposResultState.Error.toStateResource() = TopRankedUserErrorStateResources(error)