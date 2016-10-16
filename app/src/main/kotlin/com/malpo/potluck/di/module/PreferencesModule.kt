package com.malpo.potluck.di.module

import android.content.Context
import com.malpo.potluck.preferences.PreferenceStore
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(AndroidModule::class, UtilModule::class))
class PreferencesModule {

    @Provides
    @Singleton
    fun providesPreferenceStore(context: Context, moshi: Moshi): PreferenceStore {
        return PreferenceStore(context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE), moshi)
    }

    companion object {
        private val PREFS_NAME = "potluck_prefs"
    }
}
