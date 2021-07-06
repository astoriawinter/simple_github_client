package com.tatiana.rodionova.data.model.dto

data class GithubRepositoryModel(
    val incomplete_results: Boolean,
    val items: List<GithubItem>,
    val total_count: Int
)

data class GithubItem(
    val archived: Boolean,
    val created_at: String,
    val default_branch: String,
    val description: String?,
    val disabled: Boolean,
    val fork: Boolean,
    val forks: Int,
    val forks_count: Int,
    val full_name: String,
    val has_downloads: Boolean,
    val has_issues: Boolean,
    val has_pages: Boolean,
    val has_projects: Boolean,
    val has_wiki: Boolean,
    val homepage: String,
    val id: Int,
    val language: String?,
    val license: License,
    val master_branch: String,
    val name: String,
    val node_id: String,
    val open_issues: Int,
    val open_issues_count: Int,
    val owner: Owner,
    val `private`: Boolean,
    val pushed_at: String,
    val score: Int,
    val size: Int,
    val stargazers_count: Int,
    val updated_at: String,
    val watchers: Int,
    val watchers_count: Int
)

data class License(
    val html_url: String,
    val key: String,
    val name: String,
    val node_id: String,
    val spdx_id: String,
)

data class Owner(
    val avatar_url: String,
    val gravatar_id: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val site_admin: Boolean,
    val type: String,
    val url: String
)