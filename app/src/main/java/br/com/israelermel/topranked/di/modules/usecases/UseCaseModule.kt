package br.com.israelermel.topranked.di.modules.usecases

import br.com.israelermel.domain.usecase.repositories.GetGithubRepositoriesUseCase
import org.koin.dsl.module

val useCaseModule = module {

    // GitHubRepositories
    factory { GetGithubRepositoriesUseCase(get()) }

}