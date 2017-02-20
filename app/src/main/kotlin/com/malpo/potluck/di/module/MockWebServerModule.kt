package com.malpo.potluck.di.module

import com.malpo.potluck.di.qualifiers.MockWeb
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Module
class MockWebServerModule {

    val mockWebServer = MockWebServer()

    @Provides @Singleton fun provideMockWebServer(): MockWebServer = mockWebServer

    @Provides @MockWeb fun provideMockWebServerUrl(): String = mockWebServer.url("/").toString()
}
