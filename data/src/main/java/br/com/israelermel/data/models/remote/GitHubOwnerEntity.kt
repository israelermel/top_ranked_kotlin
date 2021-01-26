package br.com.israelermel.data.models.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GitHubOwnerEntity (
    @Expose @field:SerializedName("login") val login: String,
    @Expose @SerializedName("avatar_url") val avatarUrl: String
)