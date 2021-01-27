package br.com.israelermel.domain.usecase.repositories

import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.ReposRequest
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.repository.IReposRepository
import br.com.israelermel.domain.states.RequestResult
import kotlinx.coroutines.flow.Flow

class GetReposLanguageKotlinUseCase(
    private val repositoriesRepository: IReposRepository
) {
    suspend fun execute(): RequestResult<Flow<PagingData<ReposEntity>>> {
        return try {
            RequestResult.Success(repositoriesRepository.getReposLanguageKotlinResultStream())
        } catch (ex: Throwable) {
            RequestResult.Failure(
                if (ex is RepositoriesException) ex
                else RepositoriesException.UnknownRepositoriesException
            )
        }
    }
}