package com.tatiana.rodionova.tutu_assigment.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tatiana.rodionova.presentation.github_list.RepositoryListViewModel
import com.tatiana.rodionova.tutu_assigment.di.ViewModelFactory
import com.tatiana.rodionova.tutu_assigment.di.qualifier.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [UsecaseModule::class])
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RepositoryListViewModel::class)
    abstract fun bindViewModel(viewModel: RepositoryListViewModel): ViewModel
}