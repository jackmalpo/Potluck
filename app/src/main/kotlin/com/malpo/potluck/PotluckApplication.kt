package com.malpo.potluck

import android.app.Application
import com.facebook.stetho.Stetho
import com.malpo.potluck.di.component.ApplicationComponent
import com.malpo.potluck.di.component.DaggerApplicationComponent
import com.malpo.potluck.di.module.*
import timber.log.Timber


class PotluckApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        component = createComponent()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Timber.plant(Timber.DebugTree())
        }
    }

    fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .spotifyModule(SpotifyModule())
                .preferencesModule(PreferencesModule())
                .utilModule(UtilModule())
                .build()
    }

    companion object {
        internal lateinit var component: ApplicationComponent
    }


}
