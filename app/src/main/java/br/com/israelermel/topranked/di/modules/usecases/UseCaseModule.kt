package br.com.israelermel.topranked.di.modules.usecases

import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCase
import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCaseImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule = module {

    // GitHubRepositories
    factory { GetReposLanguageKotlinUseCaseImpl(get()) } bind GetReposLanguageKotlinUseCase::class

}