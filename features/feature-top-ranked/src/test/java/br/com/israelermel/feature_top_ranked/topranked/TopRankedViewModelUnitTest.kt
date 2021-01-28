package br.com.israelermel.feature_top_ranked.topranked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.feature_top_ranked.scenes.TopRankedKotlinRepositoriesViewModel
import br.com.israelermel.feature_top_ranked.states.ReposResultState
import br.com.israelermel.feature_top_ranked.topranked.providers.GetReposLanguageKotlinUseCaseSuccessFake
import br.com.israelermel.testing_core_unitest.MainCoroutineRule
import br.com.israelermel.testing_core_unitest.getOrAwaitValue
import br.com.israelermel.testing_core_unitest.observeForTesting
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.Assert.assertTrue
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.Spy

@ExperimentalCoroutinesApi
class TopRankedViewModelUnitTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @Spy
    var useCase = GetReposLanguageKotlinUseCaseSuccessFake()

    @Mock
    private lateinit var viewStateObserver : Observer<ReposResultState>

    lateinit var viewModel: TopRankedKotlinRepositoriesViewModel

    @Before
    fun setUp() {
        openMocks(this)
        viewModel = TopRankedKotlinRepositoriesViewModel(useCase)

        viewModel.resultResultState.observeForever(viewStateObserver)
    }

    @After
    fun tearDown() {
        viewModel.resultResultState.removeObserver(viewStateObserver)
    }

    @Test
    fun `should success when getGitHubRepositories return proper data`() = testCoroutineRule.runBlockingTest {
        // Given

        // When
        viewModel.getGitHubRepositories()

        // Then
        assertTrue(viewModel.resultResultState.getOrAwaitValue() is ReposResultState.Success)
    }


    @Test
    fun `should fail when getGitHubRepositories throws Empty Body exception`() = testCoroutineRule.runBlockingTest {
        // Given
        doReturn(useCase.executeEmptyRepositoriesBodyException()).`when`(useCase).execute()

        // When
        viewModel.getGitHubRepositories()

        // Then
        verify(viewStateObserver).onChanged(ReposResultState.Error(RepositoriesException.EmptyRepositoriesBodyException))
    }

    @Test
    fun `should fail when getGitHubRepositories throws Unknown exception`() = testCoroutineRule.runBlockingTest {
        // Given
        doReturn(useCase.executeUnknownRepositoriesException()).`when`(useCase).execute()

        // When
        viewModel.getGitHubRepositories()

        // Then
        verify(viewStateObserver).onChanged(ReposResultState.Error(RepositoriesException.UnknownRepositoriesException))
    }

    @Test
    fun `should fail when getGitHubRepositories throws Request exception`() = testCoroutineRule.runBlockingTest {
        // Given
        doReturn(useCase.executeRequestRepositoriesException()).`when`(useCase).execute()

        // When
        viewModel.getGitHubRepositories()

        // Then
        verify(viewStateObserver).onChanged(ReposResultState.Error(RepositoriesException.RequestRepositoriesException))
    }
}