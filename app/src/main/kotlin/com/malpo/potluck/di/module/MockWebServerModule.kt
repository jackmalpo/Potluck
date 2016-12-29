package com.malpo.potluck.di.module

import com.malpo.potluck.di.qualifiers.IsTest
import com.malpo.potluck.di.qualifiers.MockWeb
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Module(includes = arrayOf(AndroidModule::class))
class MockWebServerModule {

    val mockWebServer = MockWebServer()

    @Provides @Singleton fun provideMockWebServer(): MockWebServer = mockWebServer

    @Provides @MockWeb fun provideMockWebServerUrl(@IsTest isTest: Boolean): String {
        return if (isTest) mockWebServer.url("/").toString() else ""
    }
}
