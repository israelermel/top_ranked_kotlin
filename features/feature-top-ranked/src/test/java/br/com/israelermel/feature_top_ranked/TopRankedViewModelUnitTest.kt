package br.com.israelermel.feature_top_ranked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.ReposKeyParam
import br.com.israelermel.domain.models.repositories.ReposRequest
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCase
import br.com.israelermel.feature_top_ranked.scenes.TopRankedKotlinRepositoriesViewModel
import br.com.israelermel.feature_top_ranked.states.ReposResultState
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
import org.mockito.MockitoAnnotations.openMocks

@ExperimentalCoroutinesApi
class TopRankedViewModelUnitTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var getReposLanguageKotlinUseCase: GetReposLanguageKotlinUseCase

    lateinit var viewModel: TopRankedKotlinRepositoriesViewModel

    @Before
    fun setUp() {
        openMocks(this)
        viewModel = TopRankedKotlinRepositoriesViewModel(getReposLanguageKotlinUseCase)
    }

    @Test
    fun onSuccess() = testCoroutineRule.runBlockingTest {
        // Given
        val request = gitHubRepositoriesRequest()
        viewModel.resultResultState.observeForTesting { }

        doReturn(RequestResult.Success(emptyList<ReposBo>())).`when`(getReposLanguageKotlinUseCase).execute(any())

        // When
        viewModel.getGitHubRepositories(request)

        // Then
        assertEquals(viewModel.resultResultState.getOrAwaitValue(), ReposResultState.Success(PagingData.empty()))

    }

    @Test
    fun `empty body error`() = testCoroutineRule.runBlockingTest {
        // Given
        val exception = RepositoriesException.EmptyRepositoriesBodyException
        val request = gitHubRepositoriesRequest()
        viewModel.resultResultState.observeForTesting { }

        doReturn(RequestResult.Failure(exception)).`when`(getReposLanguageKotlinUseCase).execute(any())

        // When
        viewModel.getGitHubRepositories(request)

        // Then
        assertEquals(viewModel.resultResultState.getOrAwaitValue(), ReposResultState.Error(exception))

    }

    @Test
    fun `request error`() = testCoroutineRule.runBlockingTest {
        // Given
        val exception = RepositoriesException.RequestRepositoriesException
        val request = gitHubRepositoriesRequest()
        viewModel.resultResultState.observeForTesting { }

        doReturn(RequestResult.Failure(exception)).`when`(getReposLanguageKotlinUseCase).execute(any())

        // When
        viewModel.getGitHubRepositories(request)

        // Then
        assertEquals(viewModel.resultResultState.getOrAwaitValue(), ReposResultState.Error(exception))
    }

    @Test
    fun `unknown error`() = testCoroutineRule.runBlockingTest {
        // Given
        val exception = RepositoriesException.UnknownRepositoriesException
        val request = gitHubRepositoriesRequest()
        viewModel.resultResultState.observeForTesting { }

        doReturn(RequestResult.Failure(exception)).`when`(getReposLanguageKotlinUseCase).execute(any())

        // When
        viewModel.getGitHubRepositories(request)

        // Then
        assertEquals(viewModel.resultResultState.getOrAwaitValue(), ReposResultState.Error(exception))
    }

    private fun gitHubRepositoriesRequest(): ReposRequest {
        val params = mutableMapOf<String, String>().apply {
            put(ReposKeyParam.FILTER.value, "language:kotlin")
            put(ReposKeyParam.SORT.value, "stargazers")
            put(ReposKeyParam.PAGE.value, "1")
        }

        val request = ReposRequest(
            params = params
        )
        return request
    }


}