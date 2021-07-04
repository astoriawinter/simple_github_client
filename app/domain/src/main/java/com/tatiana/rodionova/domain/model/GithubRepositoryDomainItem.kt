package com.tatiana.rodionova.domain.model

class GithubRepositoryDomainItem(
    val name: String,
    val description: String?,
    val language: String?,
    val stargazers_count: Int,
    val updated_at: String
)