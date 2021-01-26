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


    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getSearchResultStream(request: GitHubRepositoriesRequest): Flow<PagingData<ReposEntity>> {

        val dbQuery = "kotlin"
        val pagingSourceFactory = { database.reposDao().reposByName(dbQuery) }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
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