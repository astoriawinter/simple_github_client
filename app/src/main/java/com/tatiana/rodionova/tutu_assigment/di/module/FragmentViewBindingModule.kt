package com.tatiana.rodionova.tutu_assigment.di.module

import com.tatiana.rodionova.presentation.github_detailed.RepositoryDetailedFragment
import com.tatiana.rodionova.presentation.github_list.RepositoryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentViewBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindRepositoryListFragment(): RepositoryListFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun bindRepositoryDetailedFragment(): RepositoryDetailedFragment
}