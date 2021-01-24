package br.com.israelermel.domain.repository

import br.com.israelermel.domain.models.repositories.BestProjectsKotlinRequest
import br.com.israelermel.domain.models.repositories.RepositoriesBo

interface IGitHubRepositoriesRepository {
    suspend fun getGitHubRepositories(request: BestProjectsKotlinRequest): List<RepositoriesBo>
}