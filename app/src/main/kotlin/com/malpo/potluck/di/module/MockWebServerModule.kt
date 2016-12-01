package com.malpo.potluck

import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Module
class MockWebServerModule {

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer()
}
