package com.tatiana.rodionova.tutu_assigment.di

import android.content.Context
import com.tatiana.rodionova.tutu_assigment.GithubApplication
import com.tatiana.rodionova.tutu_assigment.di.module.FragmentViewBindingModule
import com.tatiana.rodionova.tutu_assigment.di.module.NetworkModule
import com.tatiana.rodionova.tutu_assigment.di.module.UsecaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        UsecaseModule::class,
        FragmentViewBindingModule::class,
    ]
)

interface GithubComponent : AndroidInjector<GithubApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): GithubComponent
    }
}