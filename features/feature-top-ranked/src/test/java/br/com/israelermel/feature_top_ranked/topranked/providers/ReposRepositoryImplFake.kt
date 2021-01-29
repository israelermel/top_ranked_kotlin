package br.com.israelermel.feature_top_ranked.topranked.providers

import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.feature_top_ranked.topranked.providers.ReposEntityProvider
import br.com.israelermel.domain.repository.IReposRepository
import br.com.israelermel.domain.states.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReposRepositoryImplFake : IReposRepository {

    val listReposEntity = ReposEntityProvider.createListRepoEntity()

    override suspend fun getReposLanguageKotlin(): Flow<PagingData<ReposEntity>> {
        return flow {
            emit(
                PagingData.from(
                    listReposEntity
                )
            )
        }
    }

    fun executeEmptyRepositoriesBodyException() : Flow<PagingData<ReposEntity>>  {
        throw RepositoriesException.EmptyRepositoriesBodyException
    }

    fun executeUnknownRepositoriesException() : Flow<PagingData<ReposEntity>>  {
        throw RepositoriesException.UnknownRepositoriesException
    }

    fun executeRequestRepositoriesException() : Flow<PagingData<ReposEntity>>  {
        throw RepositoriesException.RequestRepositoriesException

    }

}