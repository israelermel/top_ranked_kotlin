package br.com.israelermel.domain.models.repositories

data class RepositoriesBo(
    val id: Long,
    val fullName: String? = "",
    val forksCount: Int? = 0,
    val stargazersCount: Int? = 0,
    val login: String = "",
    val avatarUrl: String = ""
)