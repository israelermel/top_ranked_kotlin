package br.com.israelermel.domain.models.repositories

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OwnerEntity (
    @Expose @field:SerializedName("login") val login: String,
    @Expose @SerializedName("avatar_url") val avatarUrl: String
)