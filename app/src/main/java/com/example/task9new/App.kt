package com.example.task9new

import android.app.Application
import com.example.task9new.di.AppComponent
import com.example.task9new.di.ContextModule
import com.example.task9new.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent : AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }
}