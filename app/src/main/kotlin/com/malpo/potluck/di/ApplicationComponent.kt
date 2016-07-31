package com.malpo.potluck.di

import com.malpo.potluck.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Contains getters / setters for classes using constructor injection
 */
@Singleton
@Component(modules = arrayOf(
        AndroidModule::class,
        FirebaseModule::class,
        SpotifyModule::class))

interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}