package com.bonface.pokespectra.core

import android.app.Application
import android.content.Context

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        private lateinit var appContext: Context
        fun getAppContext(): Context {
            return appContext
        }
    }
}