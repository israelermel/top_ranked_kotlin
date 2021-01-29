package br.com.israelermel.database

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.israelermel.database.configs.GitHubDatabase
import br.com.israelermel.domain.models.repositories.OwnerEntity
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.testing_core_unitest.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class RemoteKeysDataSourceTest {

    private lateinit var database: GitHubDatabase

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitHubDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun insertTaskAndGetById() = testCoroutineRule.runBlockingTest {
        // GIVEN
        val listOfRepos = getListOfRepoEntity()
        val repo = listOfRepos[0]

        database.reposDao().insertAll(listOfRepos)

        // WHEN
        val loaded = database.reposDao().reposByLanguage(repo.language.orEmpty())

        // THEN
        assertThat<ReposEntity>(loaded as ReposEntity, CoreMatchers.notNullValue())
        assertThat(loaded.id, `is`(repo.id))
        assertThat(loaded.language, `is`(repo.language))
        assertThat(loaded.name, `is`(repo.name))
        assertThat(loaded.stargazersCount, `is`(repo.stargazersCount))
        assertThat(loaded.owner?.avatarUrl, `is`(repo.owner?.avatarUrl))
        assertThat(loaded.owner?.login, `is`(repo.owner?.login))

    }

    private fun getListOfRepoEntity(): List<ReposEntity> {
        return listOf(
            ReposEntity(
                id = 10L,
                name = "nome do repositorio",
                stargazersCount = 1000,
                forksCount = 100,
                language = "kotlin",
                owner = OwnerEntity(
                    login = "nome do autor",
                    avatarUrl = "https://github.com/photo.png"
                )
            ),
            ReposEntity(
                id = 10L,
                name = "nome do repositorio",
                stargazersCount = 1000,
                forksCount = 100,
                language = "kotlin",
                owner = OwnerEntity(
                    login = "nome do autor",
                    avatarUrl = "https://github.com/photo.png"
                )
            )
        )
    }

}