package br.com.israelermel.data.networking.api

import br.com.israelermel.data.models.remote.ReposItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RepositoriesApi {

    @GET("search/repositories?sort=stars")
    suspend fun getRepositoriesFilterByKotlin(@QueryMap options: Map<String, String>): Response<ReposItemsResponse>
}