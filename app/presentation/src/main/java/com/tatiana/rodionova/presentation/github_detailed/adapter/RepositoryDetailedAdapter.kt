package com.tatiana.rodionova.presentation.github_detailed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tatiana.rodionova.domain.model.GithubRepositoryTreeDomainItem
import com.tatiana.rodionova.domain.model.RepositoryTypeDomain
import com.tatiana.rodionova.presentation.R
import com.tatiana.rodionova.presentation.databinding.ItemRepositoryTreeBinding
import kotlin.properties.Delegates

class RepositoryDetailedAdapter : RecyclerView.Adapter<RepositoryDetailedViewHolder>() {

    var treeList: List<GithubRepositoryTreeDomainItem> by Delegates.observable(listOf()) { _, _, list ->
        differ.submitList(list)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<GithubRepositoryTreeDomainItem>() {

        override fun areContentsTheSame(
            oldItem: GithubRepositoryTreeDomainItem,
            newItem: GithubRepositoryTreeDomainItem
        ): Boolean =
            oldItem.path == newItem.path
                    && oldItem.type == newItem.type

        override fun areItemsTheSame(
            oldItem: GithubRepositoryTreeDomainItem,
            newItem: GithubRepositoryTreeDomainItem
        ): Boolean =
            oldItem.sha == newItem.sha
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryDetailedViewHolder =
        RepositoryDetailedViewHolder(
            ItemRepositoryTreeBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: RepositoryDetailedViewHolder, position: Int) {
        val item = treeList[position]

        holder.bind(item)
    }

    override fun getItemCount() = treeList.size
}

class RepositoryDetailedViewHolder(
    private val binding: ItemRepositoryTreeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GithubRepositoryTreeDomainItem) {
        with(binding) {
            val itemDrawable = if (item.type == RepositoryTypeDomain.BLOB) {
                R.drawable.ic_file
            } else R.drawable.ic_folder

            icon.setImageDrawable(ContextCompat.getDrawable(icon.context, itemDrawable))
            fileName.text = item.path
        }
    }
}