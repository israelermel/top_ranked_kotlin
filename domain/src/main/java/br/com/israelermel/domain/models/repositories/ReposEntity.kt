package br.com.israelermel.domain.models.repositories

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repos")
data class ReposEntity(
    @Expose @PrimaryKey @field:SerializedName("id") val id: Long,
    @Expose @field:SerializedName("full_name") val name: String?,
    @Expose @field:SerializedName("language") val language: String?,
    @Expose @field:SerializedName("forks_count") val forksCount: Int?,
    @Expose @field:SerializedName("stargazers_count") val stargazersCount: Int?,
    @Expose @Embedded @field:SerializedName("owner") val owner: OwnerEntity?
)