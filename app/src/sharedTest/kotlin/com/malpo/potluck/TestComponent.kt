package com.malpo.potluck

import com.malpo.potluck.di.component.BaseComponent
import com.malpo.potluck.di.module.SpotifyModule
import com.malpo.potluck.di.qualifiers.Guest
import com.malpo.potluck.di.qualifiers.Host
import com.malpo.potluck.di.qualifiers.SpotifyToken
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.SpotifyTokenService
import com.malpo.potluck.preferences.PreferenceStore
import com.squareup.moshi.Moshi
import dagger.Component
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SpotifyModule::class))
interface TestComponent : BaseComponent {
    @Guest fun guest(): SpotifyService

    @Host fun host(): SpotifyService

    @SpotifyToken fun token(): SpotifyTokenService

    fun moshi(): Moshi

    fun mockWebServer(): MockWebServer

    fun mockPreferenceStore(): PreferenceStore
}