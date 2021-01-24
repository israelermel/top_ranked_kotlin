package br.com.israelermel.topranked

import android.app.Application
import br.com.israelermel.topranked.di.modules.infra.infraModule
import br.com.israelermel.topranked.di.modules.repositories.repositoriesModule
import br.com.israelermel.topranked.di.modules.services.remoteServicesModule
import br.com.israelermel.topranked.di.modules.usecases.useCaseModule
import br.com.israelermel.topranked.di.modules.viewmodels.viewModelsModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                getModules()
            )
        }
    }

    fun getModules(): List<Module> {
        return listOf(
            //Infra
            infraModule,

            // Services
            remoteServicesModule,

            // Repositories
            repositoriesModule,

            // UseCases
            useCaseModule,

            // ViewModels
            viewModelsModule

        )
    }

}