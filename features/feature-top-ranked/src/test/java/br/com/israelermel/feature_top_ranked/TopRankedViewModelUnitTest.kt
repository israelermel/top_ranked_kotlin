package br.com.israelermel.feature_top_ranked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesParamsEnum
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.OwnerBo
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetGithubRepositoriesUseCase
import br.com.israelermel.feature_top_ranked.core.MainCoroutineRule
import br.com.israelermel.feature_top_ranked.core.getOrAwaitValue
import br.com.israelermel.feature_top_ranked.core.observeForTesting
import br.com.israelermel.feature_top_ranked.core.runBlockingTest
import br.com.israelermel.feature_top_ranked.scenes.TopRankedListViewModel
import br.com.israelermel.feature_top_ranked.states.GitHubRepositoriesState
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

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<ApiUser>())
                .`when`(apiHelper)
                .getUsers()
            val viewModel = SingleNetworkCallViewModel(apiHelper, databaseHelper)
            viewModel.getUsers().observeForever(apiUsersObserver)
            verify(apiHelper).getUsers()
            verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            viewModel.getUsers().removeObserver(apiUsersObserver)
        }
    }


     */

    private fun getThrowable(message: String? = "error") : Throwable {
        return Throwable(message)
    }

    private fun getRepositoriesBoList(): List<RepositoriesBo> {
        val repositoriesBo = mutableListOf<RepositoriesBo>().apply {
            add(
                RepositoriesBo(
                    fullName = "teste",
                    forksCount = 1,
                    stargazersCount = 10,
                    owerResponse = OwnerBo(login = "teste", avatarUrl = "teste")
                )
            )
            add(
                RepositoriesBo(
                    fullName = "teste1",
                    forksCount = 2,
                    stargazersCount = 100,
                    owerResponse = OwnerBo(login = "teste2", avatarUrl = "teste2")
                )
            )
        }.toList()
        return repositoriesBo
    }

    private fun gitHubRepositoriesRequest(): GitHubRepositoriesRequest {
        val params = mutableMapOf<String, String>().apply {
            put(GitHubRepositoriesParamsEnum.FILTER.value, "language:kotlin")
            put(GitHubRepositoriesParamsEnum.SORT.value, "stargazers")
            put(GitHubRepositoriesParamsEnum.PAGE.value, "1")
        }

        val request = GitHubRepositoriesRequest(
            params = params
        )
        return request
    }


}