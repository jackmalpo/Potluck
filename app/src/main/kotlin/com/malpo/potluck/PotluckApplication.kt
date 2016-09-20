package com.malpo.potluck

import android.app.Application
import com.malpo.potluck.di.component.ApplicationComponent
import com.malpo.potluck.di.component.DaggerApplicationComponent
import com.malpo.potluck.di.module.AndroidModule
import com.malpo.potluck.di.module.FirebaseModule
import com.malpo.potluck.di.module.PreferencesModule
import com.malpo.potluck.di.module.SpotifyModule
import timber.log.Timber


class PotluckApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        component = createComponent()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .spotifyModule(SpotifyModule())
                .preferencesModule(PreferencesModule())
                .firebaseModule(FirebaseModule())
                .build()
    }

    companion object {
        internal lateinit var component: ApplicationComponent
    }


}
