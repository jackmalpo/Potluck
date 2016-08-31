package com.malpo.potluck

import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.guest.SpotifyGuestClient
import com.malpo.potluck.networking.spotify.guest.SpotifyGuestService
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import rx.Observable
import rx.observers.TestSubscriber

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(JUnit4::class)
class SpotifyGuestClientTest : BaseUnitTest() {

    lateinit var spotifyGuestClient: SpotifyGuestClient

    @Mock
    lateinit var mockPrefs : PreferenceStore

    @Before
    fun setup() {
        initMocks(this)

        //setup mock SpotifyGuestService
        val mockService = mock<SpotifyGuestService>()
        whenever(mockService.getAnonToken(any(), any()))
                .thenReturn(Observable.just(
                        Token(accessToken = "123",
                                expiresIn = 1,
                                token_type = "thisKind")))

        //create SpotifyGuestClient with mocked params
        spotifyGuestClient = SpotifyGuestClient(mockService, mockPrefs)
    }

    @Test
    @Throws(Exception::class)
    fun getAnonToken_savesToPrefs() {
        val testSubscriber = TestSubscriber<Token>()
        spotifyGuestClient.getAnonToken().subscribe(testSubscriber)
        verify(mockPrefs).setSpotifyGuestToken(eq("123"))
    }
}