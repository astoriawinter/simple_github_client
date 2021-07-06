package com.tatiana.rodionova.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tatiana.rodionova.data.database.dao.GithubItemDao
import com.tatiana.rodionova.data.database.dao.TreeItemDao
import com.tatiana.rodionova.data.model.entity.GithubItemEntity
import com.tatiana.rodionova.data.model.entity.TreeItemEntity

@Database(
    entities = [TreeItemEntity::class, GithubItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun githubItemDao(): GithubItemDao
    abstract fun treeDao(): TreeItemDao

}