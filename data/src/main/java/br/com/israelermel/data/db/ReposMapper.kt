package br.com.israelermel.data.db

import br.com.israelermel.domain.models.repositories.OwnerEntity
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.models.repositories.RepositoriesBo

fun RepositoriesBo.toEntity() =
    ReposEntity(
        id = id,
        forksCount = forksCount,
        name = fullName,
        stargazersCount = stargazersCount,
        language = language,
        owner = OwnerEntity(
            login = login,
            avatarUrl = avatarUrl
        )
    )

fun ReposEntity.toBo() =
    RepositoriesBo(
        id = id,
        fullName = name,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        login = owner?.login ?: "",
        avatarUrl = owner?.avatarUrl ?: "",
        language = language,
    )