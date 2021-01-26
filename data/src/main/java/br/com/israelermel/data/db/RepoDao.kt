package br.com.israelermel.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.israelermel.domain.models.repositories.RepositoriesBo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<ReposEntity>)

    @Query("SELECT * FROM repos WHERE " +
                    "full_name LIKE :full_name " +
                    "ORDER BY stargazers_count DESC, full_name ASC"
        )
    fun reposByName(full_name: String): PagingSource<Int, RepositoriesBo>

    @Query("DELETE FROM repos")
    suspend fun clearRepos()

}