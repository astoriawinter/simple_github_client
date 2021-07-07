package com.tatiana.rodionova.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tatiana.rodionova.domain.model.RepositoryTypeDomain

@Entity(tableName = "Tree")
data class TreeItemEntity(
    val fullName: String,
    val path: String,
    val type: RepositoryTypeDomain,
    @PrimaryKey(autoGenerate = false)
    val sha: String
)