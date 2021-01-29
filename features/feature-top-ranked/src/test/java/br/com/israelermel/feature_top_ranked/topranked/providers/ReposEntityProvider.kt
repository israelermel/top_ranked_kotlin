package br.com.israelermel.feature_top_ranked.topranked.providers

import br.com.israelermel.domain.models.repositories.OwnerEntity
import br.com.israelermel.domain.models.repositories.ReposEntity

object ReposEntityProvider {
    fun createListRepoEntity() : List<ReposEntity> {
        return mutableListOf(
            ReposEntity(
                id = 1,
                language = "kotlin",
                forksCount = 1000,
                stargazersCount = 100,
                name = "teste teste",
                owner = OwnerEntity(
                    login = "teste",
                    avatarUrl = "https://github.com.br"
                )
            ),
            ReposEntity(
                id = 2,
                language = "kotlin",
                forksCount = 100,
                stargazersCount = 10,
                name = "teste teste 2",
                owner = OwnerEntity(
                    login = "teste 2",
                    avatarUrl = "https://github.com.br"
                )
            )
        )
    }
}