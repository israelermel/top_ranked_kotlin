package br.com.israelermel.feature_top_ranked.topranked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.feature_top_ranked.scenes.topranked.TopRankedKotlinRepositoriesViewModel
import br.com.israelermel.feature_top_ranked.states.topranked.ReposResultState
import br.com.israelermel.feature_top_ranked.topranked.providers.GetReposLanguageKotlinUseCaseSuccessFake
import br.com.israelermel.testing_core_unitest.MainCoroutineRule
import br.com.israelermel.testing_core_unitest.getOrAwaitValue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopRankedViewModelUnitTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @Spy
    var useCase = GetReposLanguageKotlinUseCaseSuccessFake()

    @Mock
    private lateinit var viewStateObserver : Observer<ReposResultState>

    lateinit var sut: TopRankedKotlinRepositoriesViewModel

    @Before
    fun setUp() {
        openMocks(this)
        sut = TopRankedKotlinRepositoriesViewModel(useCase)

        sut.resultResultState.observeForever(viewStateObserver)
    }

    @After
    fun tearDown() {
        sut.resultResultState.removeObserver(viewStateObserver)
    }

    @Test
    fun `should success when getGitHubRepositories return proper data`() = testCoroutineRule.runBlockingTest {
        // Given

        // When
        sut.getGitHubRepositories()

        // Then
        assertTrue(sut.resultResultState.getOrAwaitValue() is ReposResultState.Success)
    }


    @Test
    fun `should fail when getGitHubRepositories throws Empty Body exception`() = testCoroutineRule.runBlockingTest {
        // Given
        doReturn(useCase.executeEmptyRepositoriesBodyException()).`when`(useCase).execute()

        // When
        sut.getGitHubRepositories()

        // Then
        verify(viewStateObserver).onChanged(ReposResultState.Error(RepositoriesException.EmptyRepositoriesBodyException))
    }

    @Test
    fun `should fail when getGitHubRepositories throws Unknown exception`() = testCoroutineRule.runBlockingTest {
        // Given
        doReturn(useCase.executeUnknownRepositoriesException()).`when`(useCase).execute()

        // When
        sut.getGitHubRepositories()

        // Then
        verify(viewStateObserver).onChanged(ReposResultState.Error(RepositoriesException.UnknownRepositoriesException))
    }

    @Test
    fun `should fail when getGitHubRepositories throws Request exception`() = testCoroutineRule.runBlockingTest {
        // Given
        doReturn(useCase.executeRequestRepositoriesException()).`when`(useCase).execute()

        // When
        sut.getGitHubRepositories()

        // Then
        verify(viewStateObserver).onChanged(ReposResultState.Error(RepositoriesException.RequestRepositoriesException))
    }
}