package com.malpo.potluck.networking.spotify.guest

import com.malpo.potluck.BaseDaggerSpek
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.SpotifyTokenService
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import rx.Observable
import rx.functions.Action1
import rx.observers.TestSubscriber
import kotlin.test.assertNotNull

@RunWith(JUnitPlatform::class)
class SpotifyGuestClientTest : BaseDaggerSpek({
    val mockService: SpotifyService = mock()
    val mockTokenService: SpotifyTokenService = mock()
    val mockPrefs: PreferenceStore = mock()

    describe("a spotify guest client") {
        val spotifyClient = SpotifyGuestClient(mockService, mockTokenService, mockPrefs)

        beforeEach {
            reset(mockPrefs, mockTokenService, mockService)
            whenever(mockTokenService.guestToken(any(), any()))
                    .thenReturn(Observable.just(Token(accessToken = "123", expiresIn = 1, token_type = "thisKind")))
        }

        it("should retrieve the guest token from the api") {
            val ts = TestSubscriber<Token>()
            spotifyClient.guestToken().subscribe(ts)
        }

        it("should save the guest token after it's retrieved") {
            var result: Token? = null
            whenever(mockPrefs.setSpotifyGuestToken()).thenReturn(Action1 { it -> result = it })

            val ts = TestSubscriber<Token>()

            spotifyClient.guestToken().subscribe(ts)
            assertNotNull(result)
        }
    }

})