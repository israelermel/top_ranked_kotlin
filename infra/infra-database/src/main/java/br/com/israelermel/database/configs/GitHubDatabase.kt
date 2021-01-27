package br.com.israelermel.database.configs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.israelermel.database.remotekeys.RemoteKeys
import br.com.israelermel.database.remotekeys.RemoteKeysDao
import br.com.israelermel.database.toprepos.RepoDao
import br.com.israelermel.domain.models.repositories.ReposEntity

@Database(
    entities = [ReposEntity::class, RemoteKeys::class],
    version = 3,
    exportSchema = false
)
abstract class GitHubDatabase : RoomDatabase() {

    abstract fun reposDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: GitHubDatabase? = null

        fun getInstance(context: Context): GitHubDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context)
                        .also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                GitHubDatabase::class.java, "Github.db"
            ).fallbackToDestructiveMigration()
                .build()
    }


}