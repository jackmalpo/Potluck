package com.malpo.potluck

import android.app.Application
import com.malpo.potluck.di.*
import timber.log.Timber


class PotluckApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DaggerHolder.instance.setDaggerComponent(createComponent())
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .firebaseModule(FirebaseModule())
                .build()
    }
}
