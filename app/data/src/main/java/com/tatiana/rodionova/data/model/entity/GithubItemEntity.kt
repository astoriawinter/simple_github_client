package com.tatiana.rodionova.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Github")
data class GithubItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    val stargazers_count: Int,
    val updated_at: String
)