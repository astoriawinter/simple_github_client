package com.tatiana.rodionova.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GithubRepositoryListDomainItem(
    val name: String,
    val description: String?,
    val language: String?,
    val stargazers_count: Int,
    val updated_at: String
): Parcelable

fun String.getNameAndRepository() =
    split("/")