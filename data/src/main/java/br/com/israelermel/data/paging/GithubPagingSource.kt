package br.com.israelermel.data.paging

import androidx.paging.PagingSource
import br.com.israelermel.data.networking.api.RepositoriesApi
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesKeyParam
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import retrofit2.HttpException
import java.io.IOException

const val GITHUB_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 50

class GithubPagingSource(
    private val service: RepositoriesApi
) : PagingSource<Int, RepositoriesBo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoriesBo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX

        return try {

            val paramsEnum = mutableMapOf<String, String>().apply {
                put(GitHubRepositoriesKeyParam.FILTER.value, "language:kotlin")
                put(GitHubRepositoriesKeyParam.SORT.value, "stars")
                put(GitHubRepositoriesKeyParam.PAGE.value, position.toString())
                put(GitHubRepositoriesKeyParam.PER_PAGE.value, params.loadSize.toString())
            }

            val response = service.getRepositories(paramsEnum)
            val repos = response.body()?.items
            val nextKey = if (repos?.isEmpty() == true) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos?.map {
                    RepositoriesBo(
                        id = it.id,
                        fullName = it.name,
                        stargazersCount = it.stargazersCount,
                        forksCount = it.forksCount,
                        login = it.owner?.login ?: "",
                        avatarUrl = it.owner?.avatarUrl ?: ""
                    )
                } ?: emptyList(),
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
