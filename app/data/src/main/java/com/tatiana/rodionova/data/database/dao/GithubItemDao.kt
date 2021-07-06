package com.tatiana.rodionova.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tatiana.rodionova.data.model.entity.GithubItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGithubItemList(githubItemEntity: List<GithubItemEntity>)

    @Query("SELECT * FROM Github ORDER BY stargazers_count DESC")
    fun getGithubItemList(): Flow<List<GithubItemEntity>>
}