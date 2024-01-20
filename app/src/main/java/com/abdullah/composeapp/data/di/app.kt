package com.abdullah.composeapp.data.di

import android.app.Application
import com.abdullah.composeapp.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        timber()
    }


    private fun timber(){
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        else {
            Timber.plant(object: Timber.Tree(){
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    // Avoid Logging in any case
                }
            })
        }
    }

}