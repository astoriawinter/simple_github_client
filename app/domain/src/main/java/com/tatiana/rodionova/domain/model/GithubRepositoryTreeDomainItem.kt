package com.tatiana.rodionova.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GithubRepositoryTreeDomainItem(
    val path: String,
    val type: RepositoryTypeDomain,
    val sha: String
): Parcelable

enum class RepositoryTypeDomain { BLOB, TREE }