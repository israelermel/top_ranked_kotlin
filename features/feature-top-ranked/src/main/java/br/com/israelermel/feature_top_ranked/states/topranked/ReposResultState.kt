package br.com.israelermel.feature_top_ranked.states.topranked

import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.ReposEntity

sealed class ReposResultState {
    object Loading : ReposResultState()
    data class Success(val repositories: PagingData<ReposEntity>) : ReposResultState()
    data class Error(val error: RepositoriesException) : ReposResultState()

}