package com.tatiana.rodionova.data.model.dto

data class GithubRepositoryModel(
    val incomplete_results: Boolean,
    val items: List<GithubItem>,
    val total_count: Int
)

data class GithubItem(
    val id: Int,
    val description: String?,
    val full_name: String,
    val language: String?,
    val pushed_at: String,
    val stargazers_count: Int
)