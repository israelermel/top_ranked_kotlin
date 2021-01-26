package br.com.israelermel.domain.repository

import androidx.paging.PagingData
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import kotlinx.coroutines.flow.Flow

interface IGitHubRepositoriesRepository {
//    suspend fun getGitHubRepositories(request: GitHubRepositoriesRequest): List<RepositoriesBo>
    suspend fun getSearchResultStream(request: GitHubRepositoriesRequest): Flow<PagingData<ReposEntity>>
}