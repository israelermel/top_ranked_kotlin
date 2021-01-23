package br.com.israelermel.topranked.di.modules.viewmodels

import br.com.israelermel.feature_top_ranked.scenes.TopRankedListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { TopRankedListViewModel(get()) }

}