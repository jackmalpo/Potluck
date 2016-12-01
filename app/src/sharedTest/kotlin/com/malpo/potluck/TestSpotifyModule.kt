package com.malpo.potluck

import com.malpo.potluck.di.module.SpotifyModule
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Module
class TestSpotifyModule : SpotifyModule() {

    val mockWebServer = MockWebServer()
    val mockWebServerUrl = mockWebServer.url("/").toString()

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = mockWebServer

    override fun provideGuestBaseUrl(): String {
        return mockWebServerUrl
    }

    override fun provideHostBaseUrl(): String {
        return mockWebServerUrl
    }

    override fun provideTokenBaseUrl(): String {
        return mockWebServerUrl
    }
}