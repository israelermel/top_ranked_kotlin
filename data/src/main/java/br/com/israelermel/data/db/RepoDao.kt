package br.com.israelermel.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.domain.models.repositories.RepositoriesBo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<ReposEntity>)

    @Query("SELECT * FROM repos WHERE repos.name LIKE :name ORDER BY repos.stargazersCount DESC ")
    fun reposByName(name: String): PagingSource<Int, ReposEntity>

    @Query("DELETE FROM repos")
    suspend fun clearRepos()

}