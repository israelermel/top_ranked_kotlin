package br.com.israelermel.database.configs

import androidx.paging.*
import br.com.israelermel.data.networking.api.RepositoriesApi
import br.com.israelermel.database.toprepos.ReposRemoteMediator
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.models.repositories.ReposRequest
import br.com.israelermel.domain.repository.IReposRepository
import kotlinx.coroutines.flow.Flow


class RemoteReposImpl(
    private val repositoriesApi: RepositoriesApi,
    private val database: GitHubDatabase
) : IReposRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getReposLanguageKotlinResultStream(): Flow<PagingData<ReposEntity>> {

        val queryFilterByKotlin = "kotlin"
        val pagingSourceFactory = { database.reposDao().reposByLanguage(queryFilterByKotlin) }

        return Pager(
            config = PagingConfig(
                pageSize = ReposRemoteMediator.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = ReposRemoteMediator.NETWORK_PAGE_SIZE + 10
            ),
            remoteMediator = ReposRemoteMediator(
                query = queryFilterByKotlin,
                service = repositoriesApi,
                gitHubDatabase = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}