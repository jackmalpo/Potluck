package com.malpo.potluck

import com.malpo.potluck.di.module.AndroidModule
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.mock
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before

open class BaseDaggerTest {

    protected lateinit var testComponent: TestComponent

    @Before
    fun initComponent() {
        testComponent = buildComponent()
    }

    fun buildComponent(): TestComponent {
        return DaggerTestComponent.builder()
                .androidModule(AndroidModule(appContext = mock(), isTest = true))
                .preferencesModule(MockPreferencesModule())
                .build()
    }

    fun mockPrefStore(): PreferenceStore = testComponent.mockPreferenceStore()
    fun mockWebServer(): MockWebServer = testComponent.mockWebServer()
    fun moshi(): Moshi = testComponent.moshi()

    fun MockWebServer.queueSuccessfulResponse(body: String) = this.enqueue(MockResponse().setResponseCode(200).setBody(body))
    fun MockWebServer.queueAuthFailure(body: String) = this.enqueue(MockResponse().setResponseCode(401).setBody(body))
    fun MockWebServer.queueFailure(body: String) = this.enqueue(MockResponse().setResponseCode(500).setBody(body))
}