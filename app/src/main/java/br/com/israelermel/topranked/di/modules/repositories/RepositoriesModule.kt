package br.com.israelermel.topranked.di.modules.repositories

import br.com.israelermel.data.db.RepoDatabase
import br.com.israelermel.data.repository.RemoteGitHubRepositoriesImpl
import br.com.israelermel.domain.repository.IGitHubRepositoriesRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoriesModule = module {

    single { RepoDatabase.getInstance(get()) }
    single { RemoteGitHubRepositoriesImpl(get(), get()) } bind IGitHubRepositoriesRepository::class

}