package br.com.israelermel.domain.repository

import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.RepositoriesBo

interface IGitHubRepositoriesRepository {
    suspend fun getGitHubRepositories(request: GitHubRepositoriesRequest): List<RepositoriesBo>
}