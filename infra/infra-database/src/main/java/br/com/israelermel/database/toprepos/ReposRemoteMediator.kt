package br.com.israelermel.database.toprepos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.israelermel.data.networking.api.RepositoriesApi
import br.com.israelermel.database.remotekeys.RemoteKeys
import br.com.israelermel.database.configs.GitHubDatabase
import br.com.israelermel.domain.models.repositories.ReposKeyParam
import br.com.israelermel.domain.models.repositories.ReposEntity
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class ReposRemoteMediator(
    private val query: String,
    private val service: RepositoriesApi,
    private val gitHubDatabase: GitHubDatabase
) : RemoteMediator<Int, ReposEntity>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ReposEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state) ?: throw InvalidObjectException("Remote key and the prevKey should not be null")
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)

                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {

            val paramsEnum = mutableMapOf<String, String>().apply {
                put(ReposKeyParam.FILTER.value, "language:${query.trim()}")
                put(ReposKeyParam.PAGE.value, page.toString())
                put(ReposKeyParam.PER_PAGE.value, state.config.pageSize.toString())
            }

            val apiResponse = service.getRepositoriesFilterByKotlin(paramsEnum)

            val repos = apiResponse.body()?.items
            val endOfPaginationReached = repos?.isEmpty() ?: false

            gitHubDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    gitHubDatabase.remoteKeysDao().clearRemoteKeys()
                    gitHubDatabase.reposDao().clearRepos()
                }

                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = repos?.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                keys?.let {
                    gitHubDatabase.remoteKeysDao().insertAll(keys)
                }

                repos?.let { reposList ->
                    gitHubDatabase.reposDao().insertAll(reposList.map { it })
                }

            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ReposEntity>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                gitHubDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ReposEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                gitHubDatabase.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ReposEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                gitHubDatabase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

    companion object {
        const val GITHUB_STARTING_PAGE_INDEX = 1
        const val NETWORK_PAGE_SIZE = 50
    }
}