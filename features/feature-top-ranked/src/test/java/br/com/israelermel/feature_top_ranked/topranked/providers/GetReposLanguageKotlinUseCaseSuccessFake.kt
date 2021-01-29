package br.com.israelermel.feature_top_ranked.topranked.providers

import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetReposLanguageKotlinUseCaseSuccessFake : GetReposLanguageKotlinUseCase {

    val listReposEntity = ReposEntityProvider.createListRepoEntity()

    override suspend fun execute(): RequestResult<Flow<PagingData<ReposEntity>>> {
        return RequestResult.Success(

            result = flow {
                emit(
                    PagingData.from(
                        listReposEntity
                    )
                )
            }
        )
    }

    fun executeEmptyRepositoriesBodyException() : RequestResult<Flow<PagingData<ReposEntity>>> {
        return RequestResult.Failure(
            RepositoriesException.EmptyRepositoriesBodyException
        )
    }

    fun executeUnknownRepositoriesException() : RequestResult<Flow<PagingData<ReposEntity>>> {
        return RequestResult.Failure(
            RepositoriesException.UnknownRepositoriesException
        )
    }

    fun executeRequestRepositoriesException() : RequestResult<Flow<PagingData<ReposEntity>>> {
        return RequestResult.Failure(
            RepositoriesException.RequestRepositoriesException
        )
    }




}