package com.malpo.potluck.di

import com.malpo.potluck.BaseComponent
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

interface ApplicationComponent : BaseComponent{
}