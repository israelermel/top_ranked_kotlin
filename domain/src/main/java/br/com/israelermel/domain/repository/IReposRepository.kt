package br.com.israelermel.domain.repository

import androidx.paging.PagingData
import br.com.israelermel.domain.models.repositories.ReposEntity
import kotlinx.coroutines.flow.Flow

interface IReposRepository {
    suspend fun getReposLanguageKotlinResultStream(): Flow<PagingData<ReposEntity>>
}