package br.com.israelermel.feature_top_ranked

import br.com.israelermel.feature_top_ranked.scenes.TopRankedListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TopRankedViewModelUnitTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Test
    fun addLikeCount() = mainCoroutineRule.runBlockingTest {
        val articleViewModel = TopRankedListViewModel()

        articleViewModel.getGitHubRepositories()
        Assert.assertEquals(1, articleViewModel.getLikeCount())
    }

}