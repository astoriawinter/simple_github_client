package com.tatiana.rodionova.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tatiana.rodionova.data.model.entity.TreeItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TreeItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTreeList(treeList: List<TreeItemEntity>)

    @Query("SELECT * FROM Tree WHERE fullName=:fullName")
    fun getTreeListByNameAndRepo(fullName: String): Flow<List<TreeItemEntity>>
}