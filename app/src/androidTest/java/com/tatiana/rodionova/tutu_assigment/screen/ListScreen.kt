package com.tatiana.rodionova.tutu_assigment.screen

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import com.tatiana.rodionova.presentation.github_list.RepositoryListFragment
import com.tatiana.rodionova.tutu_assigment.R
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object ListScreen: KScreen<ListScreen>() {

    override val layoutId get() = R.layout.fragment_repository_list
    override val viewClass = RepositoryListFragment::class.java

    val list: KRecyclerView = KRecyclerView(
        { withId(R.id.repositories) },
        { itemType(ListScreen::GithubItem) }
    )

    class GithubItem(parent: Matcher<View>) : KRecyclerItem<GithubItem>(parent) {

        val name = KTextView(parent) { withId(R.id.name) }
    }
}