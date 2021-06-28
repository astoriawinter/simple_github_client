package com.tatiana.rodionova.tutu_assigment.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface GithubComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): GithubComponent
    }
}