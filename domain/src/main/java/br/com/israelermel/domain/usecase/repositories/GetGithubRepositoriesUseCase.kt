package br.com.israelermel.domain.usecase.repositories

import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.BestProjectsKotlinRequest
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.repository.IGitHubRepositoriesRepository
import br.com.israelermel.domain.states.RequestResult

class GetGithubRepositoriesUseCase(
    private val repositoriesRepository: IGitHubRepositoriesRepository
) {
    suspend fun execute(request: BestProjectsKotlinRequest): RequestResult<List<RepositoriesBo>> {
        return try {
            RequestResult.Success(repositoriesRepository.getGitHubRepositories(request))
        } catch (ex: Throwable) {
            RequestResult.Failure(
                if (ex is RepositoriesException) ex
                else RepositoriesException.UnknownRepositoriesException
            )
        }
    }
}