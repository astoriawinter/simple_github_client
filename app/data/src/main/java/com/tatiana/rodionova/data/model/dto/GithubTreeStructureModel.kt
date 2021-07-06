package com.tatiana.rodionova.data.model.dto

import com.google.gson.annotations.SerializedName

data class TreeStructureModel(
    val sha: String,
    val tree: List<Tree>,
    val truncated: Boolean,
    val url: String
)

data class Tree(
    val mode: String,
    val path: String,
    val sha: String,
    val size: Int,
    val type: RepositoryTypeModel,
    val url: String
)

enum class RepositoryTypeModel {
    @SerializedName("blob")
    BLOB,
    @SerializedName("tree")
    TREE
}
