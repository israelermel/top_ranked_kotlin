package br.com.israelermel.domain.usecase.repositories

import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.repository.IReposRepository
import br.com.israelermel.domain.states.RequestResult
import kotlinx.coroutines.flow.Flow

class GetReposLanguageKotlinUseCaseImpl(
    private val repositoriesRepository: IReposRepository
) : GetReposLanguageKotlinUseCase {

    override suspend fun execute(): RequestResult<Flow<PagingData<ReposEntity>>> {
        return try {
            RequestResult.Success(repositoriesRepository.getReposLanguageKotlin())
        } catch (ex: Throwable) {
            RequestResult.Failure(
                if (ex is RepositoriesException) ex
                else RepositoriesException.UnknownRepositoriesException
            )
        }
    }
}