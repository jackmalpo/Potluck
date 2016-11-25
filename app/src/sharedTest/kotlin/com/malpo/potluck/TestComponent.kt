package com.malpo.potluck

import android.content.Context
import com.malpo.potluck.di.component.BaseComponent
import com.malpo.potluck.di.module.UtilModule
import com.malpo.potluck.di.qualifiers.Guest
import com.malpo.potluck.di.qualifiers.Host
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.preferences.PreferenceStore
import com.squareup.moshi.Moshi
import dagger.Component
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestSpotifyModule::class, UtilModule::class))
interface TestComponent : BaseComponent {
    @Guest fun guestSpotifyService(): SpotifyService

    @Host fun hostSpotifyService(): SpotifyService

    fun mockWebServer(): MockWebServer

    fun preferenceStore(): PreferenceStore

    fun moshi(): Moshi

    fun context(): Context
}