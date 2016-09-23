package com.malpo.potluck

import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyClient
import com.malpo.potluck.networking.spotify.SpotifyService
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
class SpotifyClientTest : BaseUnitTest() {

    lateinit var spotifyClient: SpotifyClient

    @Mock
    lateinit var mockPrefs : PreferenceStore

    @Before
    fun setup() {
        initMocks(this)

        //setup mock SpotifyService
        val mockService = mock<SpotifyService>()
        whenever(mockService.getGuestToken(any(), any()))
                .thenReturn(Observable.just(
                        Token(accessToken = "123",
                                expiresIn = 1,
                                token_type = "thisKind")))

        //create SpotifyClient with mocked params
        spotifyClient = SpotifyClient(mockService, mockPrefs)
    }

    @Test
    @Throws(Exception::class)
    fun getAnonToken_savesToPrefs() {
        val testSubscriber = TestSubscriber<Token>()
        spotifyClient.getGuestToken().subscribe(testSubscriber)
        verify(mockPrefs).setSpotifyGuestToken().call(eq("123"))
    }
}