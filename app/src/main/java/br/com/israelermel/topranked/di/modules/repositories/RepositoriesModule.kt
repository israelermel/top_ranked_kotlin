package br.com.israelermel.topranked.di.modules.repositories

import br.com.israelermel.data.repository.RemoteGitHubRepositoriesImpl
import br.com.israelermel.domain.repository.IGitHubRepositoriesRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoriesModule = module {

    single { RemoteGitHubRepositoriesImpl(get()) } bind IGitHubRepositoriesRepository::class

}