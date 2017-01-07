package com.malpo.potluck.networking.spotify.guest

import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.SpotifyTokenService
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rx.Observable
import rx.functions.Action1
import rx.observers.TestSubscriber
import kotlin.test.assertNotNull

class SpotifyGuestClientTest {

    @Mock lateinit var mockService: SpotifyService
    @Mock lateinit var mockTokenService: SpotifyTokenService
    @Mock lateinit var mockPrefs: PreferenceStore

    lateinit var guestClient: SpotifyGuestClient

    val token = Token(accessToken = "123", expiresIn = 1, token_type = "thisKind")

    @Before fun setup() {
        MockitoAnnotations.initMocks(this)

        guestClient = SpotifyGuestClient(mockService, mockTokenService, mockPrefs)

        whenever(mockTokenService.guestToken(any(), any()))
                .thenReturn(Observable.just(token))
    }

    @Test fun guestToken_retrievesToken() {
        whenever(mockPrefs.setSpotifyGuestToken()).thenReturn(Action1 {})

        val ts = TestSubscriber<Token>()
        guestClient.guestToken().subscribe(ts)
        ts.assertReceivedOnNext(listOf(token))
    }

    @Test fun guestToken_savesTokenToPrefs_afterRetrieved() {
        var result: Token? = null
        whenever(mockPrefs.setSpotifyGuestToken()).thenReturn(Action1 { it -> result = it })

        val ts = TestSubscriber<Token>()

        guestClient.guestToken().subscribe(ts)
        assertNotNull(result)
    }
}