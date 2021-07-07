package com.tatiana.rodionova.data.mapper

import com.tatiana.rodionova.data.model.dto.GithubItem
import com.tatiana.rodionova.data.model.dto.Tree
import com.tatiana.rodionova.data.model.entity.GithubItemEntity
import com.tatiana.rodionova.data.model.entity.TreeItemEntity

fun Tree.toEntity(fullName: String) =
    TreeItemEntity(
        fullName, path, type.toDomian(), sha
    )

fun GithubItem.toEntity() =
    GithubItemEntity(
        id, full_name, description, language, stargazers_count, pushed_at
    )