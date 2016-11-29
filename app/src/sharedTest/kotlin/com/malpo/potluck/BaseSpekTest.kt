package com.malpo.potluck

import android.content.Context
import com.malpo.potluck.di.module.AndroidModule
import com.malpo.potluck.di.module.UtilModule
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.mock
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.Dsl
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
open class BaseSpekTest(spec: Dsl.() -> Unit) : Spek({

    beforeEach {
        testComponent = buildComponent()
        mockWebServer().start()
    }

    group("test", body = spec)

    afterEach {
        mockWebServer().shutdown()
    }


}) {
    companion object {
        var testComponent: TestComponent = buildComponent()

        fun buildComponent(): TestComponent = DaggerTestComponent.builder()
                .androidModule(AndroidModule(mock()))
                .preferencesModule(TestPreferencesModule())
                .testSpotifyModule(TestSpotifyModule())
                .utilModule(UtilModule())
                .build()

        fun mockPrefStore(): PreferenceStore = testComponent.getPreferenceStore()
        fun mockContext(): Context = testComponent.getContext()
        fun mockWebServer(): MockWebServer = testComponent.getMockWebServer()
        fun moshi(): Moshi = testComponent.getMoshi()
    }
}

fun MockWebServer.queueSuccessfulResponse(body: String) = this.enqueue(MockResponse().setResponseCode(200).setBody(body))
fun MockWebServer.queueAuthFailure(body: String) = this.enqueue(MockResponse().setResponseCode(401).setBody(body))
fun MockWebServer.queueFailure(body: String) = this.enqueue(MockResponse().setResponseCode(500).setBody(body))