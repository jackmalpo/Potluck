package com.malpo.potluck.networking.spotify.host

import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import rx.Observable
import rx.functions.Action1
import rx.observers.TestSubscriber
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(JUnitPlatform::class)
class SpotifyHostClientTest : Spek({
    val mockPrefs: PreferenceStore = mock()
    val mockService: SpotifyService = mock()

    describe("a spotify guest client") {
        val spotifyClient = SpotifyHostClient(mockService, mockPrefs)

        beforeEach {
            reset(mockPrefs, mockService)
            whenever(mockService.hostToken(any(), any(), any(), any()))
                    .thenReturn(Observable.just(
                            Token(accessToken = "123",
                                    expiresIn = 1,
                                    token_type = "thisKind",
                                    refreshToken = "ABC")))
        }

        it("should retrieve the host token from the api") {
            val ts = TestSubscriber<Token>()
            spotifyClient.token("test_code").subscribe(ts)
        }

        it("should save the host token") {
            var result: Token? = null
            whenever(mockPrefs.setSpotifyHostToken()).thenReturn(Action1 { it -> result = it })

            val ts = TestSubscriber<Token>()
            spotifyClient.token("123").subscribe(ts)

            assertNotNull(result)
            assertEquals("ABC", result?.refreshToken)
            assertEquals("123", result?.accessToken)
            assertEquals(1, result?.expiresIn)
            assertEquals("thisKind", result?.token_type)
        }
    }
})