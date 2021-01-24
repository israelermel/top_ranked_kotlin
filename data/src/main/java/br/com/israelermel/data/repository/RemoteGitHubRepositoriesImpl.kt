package br.com.israelermel.data.repository

import br.com.israelermel.data.networking.api.RepositoriesApi
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.BestProjectsKotlinRequest
import br.com.israelermel.domain.models.repositories.OwnerBo
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.repository.IGitHubRepositoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteGitHubRepositoriesImpl(
    private val repositoriesApi: RepositoriesApi
) : IGitHubRepositoriesRepository {

    override suspend fun getGitHubRepositories(request: BestProjectsKotlinRequest): List<RepositoriesBo> =
        withContext(Dispatchers.IO) {

            try {
                val response = repositoriesApi.getRepositories(request.params)

                if (response.isSuccessful) {
                    response.body()?.response?.run {
                        this.map {
                            RepositoriesBo(
                                fullName = it.fullName,
                                forksCount = it.forksCount,
                                stargazersCount = it.stargazersCount,
                                owerResponse = OwnerBo(
                                    login = it.gitHubOwnerResponse.login,
                                    avatarUrl = it.gitHubOwnerResponse.avatarUrl
                                )
                            )
                        }
                    } ?: throw RepositoriesException.EmptyRepositoriesBodyException

                } else {
                    throw RepositoriesException.UnknownRepositoriesException
                }

            } catch (ex: NullPointerException) {
                throw RepositoriesException.RequestRepositoriesException
            }

        }


}