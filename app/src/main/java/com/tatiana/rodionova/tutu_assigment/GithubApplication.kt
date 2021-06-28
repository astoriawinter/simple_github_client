package com.tatiana.rodionova.tutu_assigment

import android.app.Application
import com.tatiana.rodionova.tutu_assigment.di.DaggerGithubComponent

class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DaggerGithubComponent
            .factory()
            .create(applicationContext)
    }
}