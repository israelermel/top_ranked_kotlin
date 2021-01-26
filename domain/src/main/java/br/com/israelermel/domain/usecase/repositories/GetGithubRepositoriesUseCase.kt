package br.com.israelermel.domain.usecase.repositories

import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.repository.IGitHubRepositoriesRepository
import br.com.israelermel.domain.states.RequestResult
import kotlinx.coroutines.flow.Flow

class GetGithubRepositoriesUseCase(
    private val repositoriesRepository: IGitHubRepositoriesRepository
) {
    suspend fun execute(request: GitHubRepositoriesRequest): RequestResult<Flow<PagingData<ReposEntity>>> {
        return try {
            RequestResult.Success(repositoriesRepository.getReposLanguageKotlinResultStream(request))
        } catch (ex: Throwable) {
            RequestResult.Failure(
                if (ex is RepositoriesException) ex
                else RepositoriesException.UnknownRepositoriesException
            )
        }
    }
}