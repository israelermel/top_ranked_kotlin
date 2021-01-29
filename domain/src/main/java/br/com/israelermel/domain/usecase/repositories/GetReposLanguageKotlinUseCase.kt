package br.com.israelermel.domain.usecase.repositories

import androidx.paging.PagingData
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.states.RequestResult
import kotlinx.coroutines.flow.Flow

interface GetReposLanguageKotlinUseCase {
    suspend fun execute(): RequestResult<Flow<PagingData<ReposEntity>>>
}