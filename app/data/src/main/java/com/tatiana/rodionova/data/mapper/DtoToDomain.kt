package com.tatiana.rodionova.data.mapper

import com.tatiana.rodionova.data.model.GithubItem
import com.tatiana.rodionova.domain.model.GithubRepositoryDomainItem

fun GithubItem.toDomain() =
    GithubRepositoryDomainItem(
        full_name, description, language, stargazers_count, pushed_at
    )