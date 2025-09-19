package com.nani.movies

import android.app.Application
import com.nani.movies.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MoviesApplication)
            modules(appModule)
        }
    }
}