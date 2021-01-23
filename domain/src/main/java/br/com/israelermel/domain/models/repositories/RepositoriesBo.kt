package br.com.israelermel.domain.models.repositories

data class RepositoriesBo(
    val fullName: String,
    val forksCount: Int,
    val stargazersCount: Int,
    val owerResponse: OwnerBo
)