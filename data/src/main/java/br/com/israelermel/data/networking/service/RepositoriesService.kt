package br.com.israelermel.data.networking.service

import br.com.israelermel.data.models.remote.RemoteGitHubRepositoriesItemsResponse
import br.com.israelermel.data.models.remote.RemoteGitHubRepositoriesResponse
import br.com.israelermel.data.networking.api.RepositoriesApi
import retrofit2.Response
import retrofit2.Retrofit

const val IN_QUALIFIER = "in:full_name,stargazers_count"

class RepositoriesService(private val retrofit: Retrofit) : RepositoriesApi {

    override suspend fun getRepositories(options: Map<String, String>): Response<RemoteGitHubRepositoriesItemsResponse> {
        return retrofit.create(RepositoriesApi::class.java).getRepositories(options)
    }
}