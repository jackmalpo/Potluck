package com.malpo.potluck

import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.guest.SpotifyGuestClient
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import rx.Observable
import rx.functions.Action1
import rx.observers.TestSubscriber
import kotlin.test.assertNotNull

class SpotifyGuestClientTest : BaseUnitTest() {

    lateinit var spotifyClient: SpotifyGuestClient

    @Mock
    lateinit var mockPrefs: PreferenceStore

    @Mock
    lateinit var mockService: SpotifyService

    @Before
    fun setup() {
        initMocks(this)
        //create SpotifyGuestClient with mocked params
        spotifyClient = SpotifyGuestClient(mockService, mockPrefs)
    }

    @Test
    fun guestToken_savesToPrefs() {
        whenever(mockService.guestToken(any(), any()))
                .thenReturn(Observable.just(
                        Token(accessToken = "123",
                                expiresIn = 1,
                                token_type = "thisKind")))
        var result: Token? = null
        whenever(mockPrefs.setSpotifyGuestToken()).thenReturn(Action1 { it -> result = it })
        val ts = TestSubscriber<Token>()
        spotifyClient.guestToken().subscribe(ts)
        assertNotNull(result)
    }
}