package br.com.israelermel.domain.repository

import androidx.paging.PagingData
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.ReposEntity
import kotlinx.coroutines.flow.Flow

interface IGitHubRepositoriesRepository {
    suspend fun getReposLanguageKotlinResultStream(request: GitHubRepositoriesRequest): Flow<PagingData<ReposEntity>>
}