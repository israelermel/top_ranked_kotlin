package br.com.israelermel.data.models.remote

import br.com.israelermel.domain.models.repositories.ReposEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RemoteGitHubRepositoriesItemsResponse(
    @Expose @SerializedName("incomplete_results") val incompleteResults: Boolean = false,
    @Expose @SerializedName("message") val message: String = "",
    @Expose  @SerializedName("items") val items: List<ReposEntity> = emptyList(),
    val nextPage: Int? = null
)