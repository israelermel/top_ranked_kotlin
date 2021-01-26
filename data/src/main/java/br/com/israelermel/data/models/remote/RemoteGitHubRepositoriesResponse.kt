package br.com.israelermel.data.models.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RemoteGitHubRepositoriesResponse(
    @Expose @SerializedName("id") val id: Long,
    @Expose @SerializedName("full_name") val fullName: String,
    @Expose @SerializedName("forks_count") val forksCount: Int,
    @Expose @SerializedName("stargazers_count") val stargazersCount: Int,
    @Expose @SerializedName("owner") val gitHubOwnerEntity: GitHubOwnerEntity
)