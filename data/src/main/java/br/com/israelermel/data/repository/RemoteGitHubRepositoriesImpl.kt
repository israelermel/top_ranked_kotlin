package br.com.israelermel.data.repository

import androidx.paging.*
import br.com.israelermel.data.db.GithubRemoteMediator
import br.com.israelermel.data.db.RepoDatabase
import br.com.israelermel.data.networking.api.RepositoriesApi
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesKeyParam
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.OwnerBo
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.repository.IGitHubRepositoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class RemoteGitHubRepositoriesImpl(
    private val repositoriesApi: RepositoriesApi,
    private val database: RepoDatabase
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
                                    login = it.gitHubOwnerEntity.login,
                                    avatarUrl = it.gitHubOwnerEntity.avatarUrl
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

        val dbQuery = "%${request.params[GitHubRepositoriesKeyParam.FILTER.value]?.replace(' ', '%')}%"
        val pagingSourceFactory =  { database.reposDao().reposByName(dbQuery)}


        return Pager(
            config = PagingConfig(
                pageSize = request.params[GitHubRepositoriesKeyParam.PER_PAGE.value]?.toInt() ?: NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = GithubRemoteMediator(
                query = dbQuery,
                service = repositoriesApi,
                repoDatabase = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

}