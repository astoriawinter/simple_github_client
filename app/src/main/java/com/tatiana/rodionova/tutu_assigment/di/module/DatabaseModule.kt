package com.tatiana.rodionova.tutu_assigment.di.module

import android.content.Context
import androidx.room.Room
import com.tatiana.rodionova.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesGithubItemDao(db: AppDatabase) =
        db.githubItemDao()

    @Singleton
    @Provides
    fun providesTreeItemDao(db: AppDatabase) =
        db.treeDao()

    companion object {
        const val DATABASE_NAME = "Github.db"
    }
}