package br.com.israelermel.data.db

import br.com.israelermel.data.models.remote.RemoteGitHubRepositoriesResponse

fun RemoteGitHubRepositoriesResponse.toEntity() =
            ReposEntity(
                id = id,
                forksCount = forksCount,
                name = fullName,
                stargazersCount = stargazersCount,
                avatarUrl = gitHubOwnerEntity.avatarUrl,
                login = gitHubOwnerEntity.login
            )