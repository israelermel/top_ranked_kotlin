package br.com.israelermel.feature_top_ranked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.BestProjectsKotlinParam
import br.com.israelermel.domain.models.repositories.BestProjectsKotlinRequest
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetGithubRepositoriesUseCase
import br.com.israelermel.feature_top_ranked.scenes.TopRankedListViewModel
import br.com.israelermel.feature_top_ranked.states.GitHubRepositoriesState
import br.com.israelermel.testing_core_unitest.MainCoroutineRule
import br.com.israelermel.testing_core_unitest.getOrAwaitValue
import br.com.israelermel.testing_core_unitest.observeForTesting
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TopRankedViewModelUnitTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase

    lateinit var viewModel: TopRankedListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TopRankedListViewModel(getGithubRepositoriesUseCase)
    }

    @Test
    fun onSuccess() = testCoroutineRule.runBlockingTest {
        // Given
        val request = gitHubRepositoriesRequest()
        viewModel.resultState.observeForTesting { }

        doReturn(RequestResult.Success(emptyList<RepositoriesBo>())).`when`(getGithubRepositoriesUseCase).execute(any())

        // When
        viewModel.getGitHubRepositories(request)

        // Then
        assertEquals(viewModel.resultState.getOrAwaitValue(), GitHubRepositoriesState.Success(emptyList()))

    }

    @Test
    fun `empty body error`() = testCoroutineRule.runBlockingTest {
        // Given
        val exception = RepositoriesException.EmptyRepositoriesBodyException
        val request = gitHubRepositoriesRequest()
        viewModel.resultState.observeForTesting { }

        doReturn(RequestResult.Failure(exception)).`when`(getGithubRepositoriesUseCase).execute(any())

        // When
        viewModel.getGitHubRepositories(request)

        // Then
        assertEquals(viewModel.resultState.getOrAwaitValue(), GitHubRepositoriesState.Error(exception))

    }

    @Test
    fun `request error`() = testCoroutineRule.runBlockingTest {
        // Given
        val exception = RepositoriesException.RequestRepositoriesException
        val request = gitHubRepositoriesRequest()
        viewModel.resultState.observeForTesting { }

        doReturn(RequestResult.Failure(exception)).`when`(getGithubRepositoriesUseCase).execute(any())

        // When
        viewModel.getGitHubRepositories(request)

        // Then
        assertEquals(viewModel.resultState.getOrAwaitValue(), GitHubRepositoriesState.Error(exception))
    }

    @Test
    fun `unknown error`() = testCoroutineRule.runBlockingTest {
        // Given
        val exception = RepositoriesException.UnknownRepositoriesException
        val request = gitHubRepositoriesRequest()
        viewModel.resultState.observeForTesting { }

        doReturn(RequestResult.Failure(exception)).`when`(getGithubRepositoriesUseCase).execute(any())

        // When
        viewModel.getGitHubRepositories(request)

        // Then
        assertEquals(viewModel.resultState.getOrAwaitValue(), GitHubRepositoriesState.Error(exception))
    }

    /*

    argumentCaptor<String>().apply {
        verify(formValidationUseCase).validateQuoteInstallmentsExpirationForSubscription(capture())
        val dayCaptured = firstValue
        assertThat(day, `is`(dayCaptured))
    }
   */


    private fun gitHubRepositoriesRequest(): BestProjectsKotlinRequest {
        val params = mutableMapOf<String, String>().apply {
            put(BestProjectsKotlinParam.FILTER.value, "language:kotlin")
            put(BestProjectsKotlinParam.SORT.value, "stargazers")
            put(BestProjectsKotlinParam.PAGE.value, "1")
        }

        val request = BestProjectsKotlinRequest(
            params = params
        )
        return request
    }


}