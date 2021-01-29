package br.com.israelermel.topranked.di.modules.repositories

import br.com.israelermel.database.configs.GitHubDatabase
import br.com.israelermel.database.configs.RemoteReposImpl
import br.com.israelermel.domain.repository.IReposRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoriesModule = module {

    single { GitHubDatabase.getInstance(get()) }
    single { RemoteReposImpl(get(), get()) } bind IReposRepository::class

}