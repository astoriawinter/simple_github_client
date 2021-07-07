package com.tatiana.rodionova.data.mapper

import com.tatiana.rodionova.data.model.entity.GithubItemEntity
import com.tatiana.rodionova.data.model.entity.TreeItemEntity
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem

fun TreeItemEntity.toDomain() =
    GithubRepositoryTreeDomainItem(
        path, type, sha
    )

fun GithubItemEntity.toDomain() =
    GithubRepositoryListDomainItem(
        name, description, language, stargazers_count, updated_at
    )