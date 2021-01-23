package br.com.israelermel.data.networking.api

import br.com.israelermel.data.models.remote.RemoteGitHubRepositoriesItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RepositoriesApi {

    @GET("search/repositories")
    suspend fun getRepositories(@QueryMap options: Map<String, String>): Response<RemoteGitHubRepositoriesItemsResponse>
}