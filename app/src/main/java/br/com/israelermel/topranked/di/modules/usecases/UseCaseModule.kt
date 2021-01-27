package br.com.israelermel.topranked.di.modules.usecases

import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCase
import org.koin.dsl.module

val useCaseModule = module {

    // GitHubRepositories
    factory { GetReposLanguageKotlinUseCase(get()) }

}