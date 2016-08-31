package com.malpo.potluck.di.component

import com.malpo.potluck.di.module.AndroidModule
import com.malpo.potluck.di.module.FirebaseModule
import com.malpo.potluck.di.module.PreferencesModule
import com.malpo.potluck.di.module.SpotifyModule
import dagger.Component
import javax.inject.Singleton

/**
 * Contains getters / setters for classes using constructor injection
 */
@Singleton
@Component(modules = arrayOf(
        AndroidModule::class,
        FirebaseModule::class,
        SpotifyModule::class,
        PreferencesModule::class))

interface ApplicationComponent : BaseComponent {
    fun newActivityState(): ActivityStateComponent
}