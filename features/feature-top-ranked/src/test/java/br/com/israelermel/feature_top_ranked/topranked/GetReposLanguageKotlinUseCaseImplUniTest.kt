package br.com.israelermel.feature_top_ranked.topranked

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.israelermel.domain.exceptions.RepositoriesException
import br.com.israelermel.domain.states.RequestResult
import br.com.israelermel.domain.usecase.repositories.GetReposLanguageKotlinUseCaseImpl
import br.com.israelermel.feature_top_ranked.topranked.providers.ReposRepositoryImplFake
import br.com.israelermel.testing_core_unitest.MainCoroutineRule
import com.nhaarman.mockitokotlin2.doReturn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetReposLanguageKotlinUseCaseImplUniTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @Spy
    var repository = ReposRepositoryImplFake()

    lateinit var sut: GetReposLanguageKotlinUseCaseImpl

    @Before
    fun setUp() {
        openMocks(this)

        sut = GetReposLanguageKotlinUseCaseImpl(repository)
    }

    @Test
    fun `should success when getReposLanguageKotlin return proper data`() = testCoroutineRule.runBlockingTest {
        //GIVEN

        //WHEN
        val result = sut.execute()

        //THEN
        Assert.assertTrue(result is  RequestResult.Success)
    }

    @Test(expected= RepositoriesException.EmptyRepositoriesBodyException::class)
    fun `should fail when getReposLanguageKotlin throws Empty Body exception`() = testCoroutineRule.runBlockingTest {
        //GIVEN
        doReturn(repository.executeEmptyRepositoriesBodyException()).`when`(repository).getReposLanguageKotlin()

        //WHEN
        sut.execute()

        //THEN
    }

    @Test(expected= RepositoriesException.UnknownRepositoriesException::class)
    fun `should fail when getReposLanguageKotlin throws Unknown exception`() = testCoroutineRule.runBlockingTest {
        //GIVEN
        doReturn(repository.executeUnknownRepositoriesException()).`when`(repository).getReposLanguageKotlin()

        //WHEN
        sut.execute()

        //THEN
    }

    @Test(expected= RepositoriesException.RequestRepositoriesException::class)
    fun `should fail when getReposLanguageKotlin throws Request exception`() = testCoroutineRule.runBlockingTest {
        //GIVEN
        doReturn(repository.executeRequestRepositoriesException()).`when`(repository).getReposLanguageKotlin()

        //WHEN
        sut.execute()

        //THEN
    }
}