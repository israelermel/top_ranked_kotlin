package br.com.israelermel.data.models.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RemoteGitHubRepositoriesItemsResponse(
    @Expose  @SerializedName("items") val response: List<RemoteGitHubRepositoriesResponse>
)