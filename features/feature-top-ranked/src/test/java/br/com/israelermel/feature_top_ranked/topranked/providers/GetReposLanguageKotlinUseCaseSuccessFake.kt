package br.com.israelermel.feature_top_ranked.topranked.providers

import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.OwnerEntity
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetReposLanguageKotlinUseCaseSuccessFake : GetReposLanguageKotlinUseCase {

    val listReposEntity = createListRepoEntity()

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

    private fun createListRepoEntity() : List<ReposEntity> {
        return mutableListOf(
            ReposEntity(
                id = 1,
                language = "kotlin",
                forksCount = 1000,
                stargazersCount = 100,
                name = "teste teste",
                owner = OwnerEntity(
                    login = "teste",
                    avatarUrl = "https://github.com.br"
                )
            ),
            ReposEntity(
                id = 2,
                language = "kotlin",
                forksCount = 100,
                stargazersCount = 10,
                name = "teste teste 2",
                owner = OwnerEntity(
                    login = "teste 2",
                    avatarUrl = "https://github.com.br"
                )
            )
        )
    }


}