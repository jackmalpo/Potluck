package com.malpo.potluck.di.component

import com.malpo.potluck.di.module.*
import dagger.Component
import javax.inject.Singleton

/**
 * Contains getters / setters for classes using constructor injection
 */
@Singleton
@Component(modules = arrayOf(
        AndroidModule::class,
        SpotifyModule::class,
        PreferencesModule::class,
        UtilModule::class))

interface ApplicationComponent : BaseComponent {
    fun newActivityState(): ActivityStateComponent
}