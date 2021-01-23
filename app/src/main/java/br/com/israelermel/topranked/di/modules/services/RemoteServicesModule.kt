package br.com.israelermel.topranked.di.modules.services

import br.com.israelermel.data.networking.api.RepositoriesApi
import br.com.israelermel.data.networking.service.RepositoriesService
import org.koin.dsl.bind
import org.koin.dsl.module

val remoteServicesModule  = module {

    // RepositoriesService
    single { RepositoriesService(get()) } bind RepositoriesApi::class
}