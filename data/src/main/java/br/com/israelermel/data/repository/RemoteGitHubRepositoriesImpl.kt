package br.com.israelermel.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import br.com.israelermel.data.networking.api.RepositoriesApi
import br.com.israelermel.data.paging.GithubPagingSource
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.OwnerBo
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.repository.IGitHubRepositoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RemoteGitHubRepositoriesImpl(
    private val repositoriesApi: RepositoriesApi
) : IGitHubRepositoriesRepository {

    override suspend fun getGitHubRepositories(request: GitHubRepositoriesRequest): List<RepositoriesBo> =
        withContext(Dispatchers.IO) {
            try {
                val response = repositoriesApi.getRepositories(request.params)

                if (response.isSuccessful) {
                    response.body()?.items?.run {
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

    override suspend fun getSearchResultStream(request: GitHubRepositoriesRequest): Flow<PagingData<RepositoriesBo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { GithubPagingSource(repositoriesApi) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

}