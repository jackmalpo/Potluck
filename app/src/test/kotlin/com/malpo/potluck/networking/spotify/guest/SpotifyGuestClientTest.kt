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
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subscribers.TestSubscriber

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
        whenever(mockPrefs.setSpotifyGuestToken()).thenReturn(Consumer {})

        guestClient.guestToken().test().assertResult(token)
    }

    @Test fun guestToken_savesTokenToPrefs_afterRetrieved() {
        var result: Token? = null
        whenever(mockPrefs.setSpotifyGuestToken()).thenReturn(Consumer { it -> result = it })

        guestClient.guestToken().test()
        assertNotNull(result)
    }
}