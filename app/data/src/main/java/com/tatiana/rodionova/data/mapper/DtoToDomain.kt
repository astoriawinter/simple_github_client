package com.tatiana.rodionova.data.mapper

import com.tatiana.rodionova.data.model.GithubItem
import com.tatiana.rodionova.data.model.RepositoryTypeModel
import com.tatiana.rodionova.data.model.Tree
import com.tatiana.rodionova.domain.model.GithubRepositoryListDomainItem
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.model.RepositoryTypeDomain

fun GithubItem.toDomain() =
    GithubRepositoryListDomainItem(
        full_name, description, language, stargazers_count, pushed_at
    )

fun Tree.toDomain() =
    GithubRepositoryTreeDomainItem(
        path, type.toDomian(), sha
    )

fun RepositoryTypeModel.toDomian(): RepositoryTypeDomain =
    when (this) {
        RepositoryTypeModel.BLOB -> RepositoryTypeDomain.BLOB
        RepositoryTypeModel.TREE -> RepositoryTypeDomain.TREE
    }
