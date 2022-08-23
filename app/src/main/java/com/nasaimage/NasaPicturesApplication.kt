package com.nasaimage

import android.app.Application
import com.nasaimage.di.applicationModule
import com.nasaimage.di.repositoryModules
import com.nasaimage.di.serviceModules
import com.nasaimage.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NasaPicturesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NasaPicturesApplication)
            modules(listOf(applicationModule, serviceModules, repositoryModules, viewModelModules))
        }
    }
}