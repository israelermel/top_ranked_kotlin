package br.com.israelermel.feature_top_ranked.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesParamsEnum
import br.com.israelermel.domain.models.repositories.GitHubRepositoriesRequest
import br.com.israelermel.domain.models.repositories.OwnerBo
import br.com.israelermel.domain.models.repositories.RepositoriesBo
import br.com.israelermel.domain.usecase.repositories.GetGithubRepositoriesUseCase
import br.com.israelermel.feature_top_ranked.TestCoroutineRule
import br.com.israelermel.feature_top_ranked.scenes.TopRankedListViewModel
import br.com.israelermel.feature_top_ranked.states.GitHubRepositoriesState
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopRankedViewModelUnitTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase

    lateinit var viewModel : TopRankedListViewModel

    @Mock
    private lateinit var repositoriesUseCaseObserver: Observer<GitHubRepositoriesState>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TopRankedListViewModel(getGithubRepositoriesUseCase)
    }

    @Test
    fun addLikeCount() = testCoroutineRule.runBlockingTest {

        val request = gitHubRepositoriesRequest()

        doReturn(emptyList<RepositoriesBo>()).`when`(getGithubRepositoriesUseCase).execute(any())

        viewModel.resultState.observeForever(repositoriesUseCaseObserver)

        viewModel.getGitHubRepositories(request)

        verify(repositoriesUseCaseObserver).onChanged(GitHubRepositoriesState.Success(emptyList()))

        viewModel.resultState.removeObserver(repositoriesUseCaseObserver)
    }


    /*

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