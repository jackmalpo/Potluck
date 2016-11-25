package com.malpo.potluck

import com.malpo.potluck.di.module.AndroidModule
import com.malpo.potluck.di.module.UtilModule
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.Dsl
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
open class BaseSpekTest(spec: Dsl.() -> Unit) : Spek({
    beforeEach { testComponent = buildComponent() }
    group("test", body = spec)
}) {
    companion object {
        var testComponent: TestComponent = buildComponent()
        fun buildComponent(): TestComponent = DaggerTestComponent.builder()
                .androidModule(AndroidModule(mock()))
                .preferencesModule(TestPreferencesModule())
                .testSpotifyModule(TestSpotifyModule())
                .utilModule(UtilModule())
                .build()
    }
}