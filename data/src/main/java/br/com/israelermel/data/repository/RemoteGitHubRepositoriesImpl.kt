package br.com.israelermel.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.israelermel.data.paging.GithubRemoteMediator
import br.com.israelermel.data.db.RepoDatabase
import br.com.israelermel.data.networking.api.RepositoriesApi
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
        val pagingSourceFactory = { database.reposDao().reposByLanguage(dbQuery) }

        return Pager(
            config = PagingConfig(
                pageSize = GithubRemoteMediator.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = GithubRemoteMediator.NETWORK_PAGE_SIZE + 10
            ),
            remoteMediator = GithubRemoteMediator(
                query = dbQuery,
                service = repositoriesApi,
                repoDatabase = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}