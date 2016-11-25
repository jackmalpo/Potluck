package com.malpo.potluck

import android.content.Context
import com.malpo.potluck.di.module.PreferencesModule
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.mock
import com.squareup.moshi.Moshi

class TestPreferencesModule : PreferencesModule() {
    override fun providesPreferenceStore(context: Context, moshi: Moshi): PreferenceStore = mock()
}