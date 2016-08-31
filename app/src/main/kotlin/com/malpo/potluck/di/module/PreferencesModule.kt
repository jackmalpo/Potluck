package com.malpo.potluck.di.module

import android.content.Context
import com.malpo.potluck.preferences.PreferenceStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(AndroidModule::class))
class PreferencesModule {

    @Provides
    @Singleton
    fun providesPreferenceStore(context: Context): PreferenceStore {
        return PreferenceStore(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE))
    }

    companion object {
        private val PREFS_NAME = "potluck_prefs"
    }
}