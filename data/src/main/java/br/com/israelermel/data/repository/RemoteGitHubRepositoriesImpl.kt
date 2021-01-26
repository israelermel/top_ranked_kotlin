package br.com.israelermel.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.israelermel.data.db.GithubRemoteMediator
import br.com.israelermel.data.db.RepoDatabase
import br.com.israelermel.data.networking.api.RepositoriesApi
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesKeyParam
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.repository.IGitHubRepositoriesRepository
import kotlinx.coroutines.flow.Flow


class RemoteGitHubRepositoriesImpl(
    private val repositoriesApi: RepositoriesApi,
    private val database: RepoDatabase
) : IGitHubRepositoriesRepository {

//    override suspend fun getGitHubRepositories(request: GitHubRepositoriesRequest): List<RepositoriesBo> =
//        withContext(Dispatchers.IO) {
//            try {
//                val response = repositoriesApi.getRepositories(request.params)
//
//                if (response.isSuccessful) {
//                    response.body()?.items?.run {
//                        this.map {
//                            RepositoriesBo(
//                                id = it.id,
//                                fullName = it.name,
//                                forksCount = it.forksCount,
//                                stargazersCount = it.stargazersCount,
//                                login = it.owner?.login ?: "",
//                                avatarUrl = it.owner?.avatarUrl ?: ""
//                            )
//                        }
//                    } ?: throw RepositoriesException.EmptyRepositoriesBodyException
//
//                } else {
//                    throw RepositoriesException.UnknownRepositoriesException
//                }
//
//            } catch (ex: NullPointerException) {
//                throw RepositoriesException.RequestRepositoriesException
//            }
//        }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getSearchResultStream(request: GitHubRepositoriesRequest): Flow<PagingData<ReposEntity>> {

//        val dbQuery = "%${request.params[GitHubRepositoriesKeyParam.FILTER.value]?.replace(' ', '%')}%"
        val dbQuery = request.params[GitHubRepositoriesKeyParam.FILTER.value].toString()
        val pagingSourceFactory = { database.reposDao().reposByName(request.params[GitHubRepositoriesKeyParam.FILTER.value].toString()) }

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