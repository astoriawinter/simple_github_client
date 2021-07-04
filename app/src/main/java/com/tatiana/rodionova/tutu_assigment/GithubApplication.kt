package com.tatiana.rodionova.tutu_assigment

import com.tatiana.rodionova.tutu_assigment.di.DaggerGithubComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class GithubApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerGithubComponent.factory().create(applicationContext)
}