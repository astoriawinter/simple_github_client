package com.tatiana.rodionova.presentation.github_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tatiana.rodionova.domain.model.GithubRepositoryDomainItem
import com.tatiana.rodionova.presentation.databinding.ItemRepositoryListBinding
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.properties.Delegates

class RepositoryListAdapter : RecyclerView.Adapter<RepositoryListViewHolder>() {

    var githubItemList: List<GithubRepositoryDomainItem> by Delegates.observable(listOf()) { _, _, list ->
        differ.submitList(list)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<GithubRepositoryDomainItem>() {

        override fun areContentsTheSame(
            oldItem: GithubRepositoryDomainItem,
            newItem: GithubRepositoryDomainItem
        ): Boolean =
            oldItem.stargazers_count == newItem.stargazers_count
                    && oldItem.updated_at == newItem.updated_at
                    && oldItem.description == newItem.description
                    && oldItem.language == newItem.language

        override fun areItemsTheSame(
            oldItem: GithubRepositoryDomainItem,
            newItem: GithubRepositoryDomainItem
        ): Boolean =
            oldItem.name == newItem.name
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListViewHolder =
        RepositoryListViewHolder(
            ItemRepositoryListBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    override fun onBindViewHolder(holder: RepositoryListViewHolder, position: Int) {
        holder.bind(githubItemList[position])
    }

    override fun getItemCount() = githubItemList.size
}

class RepositoryListViewHolder(
    private val binding: ItemRepositoryListBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val iso8061 = SimpleDateFormat(ISO_8061_FORMAT, Locale.getDefault())
    private val yearMonthDay = SimpleDateFormat(YEAR_MONTH_DAY_FORMAT, Locale.getDefault())

    fun bind(item: GithubRepositoryDomainItem) {
        with(binding) {
            name.text = item.name
            item.description.run {
                description.setWithCheck(item = this)
            }
            item.stargazers_count.run {
                stargazerCount.setWithCheck(
                    item = toString(),
                    check = this != 0,
                    view = stargazerGroup
                )
            }
            item.language.run {
                language.setWithCheck(
                    item = this,
                    view = languageGroup
                )
            }
            lastUpdated.text = iso8061.parse(item.updated_at)?.let(yearMonthDay::format)
        }
    }

    private fun TextView.setWithCheck(
        item: String?,
        check: Boolean = item?.isNotBlank() == true,
        view: View = this
    ) {
        if (check) {
            text = item
            visibility = View.VISIBLE
        } else view.visibility = View.GONE
    }

    companion object {
        const val ISO_8061_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val YEAR_MONTH_DAY_FORMAT = "EEE, MMM d, ''yy"
    }
}