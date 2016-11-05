package com.malpo.potluck.networking.spotify.host

import com.malpo.potluck.BaseUnitTest
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.host.SpotifyHostClient
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
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SpotifyHostClientTest : BaseUnitTest() {

    lateinit var spotifyClient: SpotifyHostClient

    @Mock
    lateinit var mockPrefs: PreferenceStore

    @Mock
    lateinit var mockService: SpotifyService

    @Before
    fun setup() {
        initMocks(this)
        //create SpotifyGuestClient with mocked params
        spotifyClient = SpotifyHostClient(mockService, mockPrefs)
    }

    @Test
    fun guestToken_savesToPrefs() {
        whenever(mockService.hostToken(any(), any(), any(), any()))
                .thenReturn(Observable.just(
                        Token(accessToken = "123",
                                expiresIn = 1,
                                token_type = "thisKind",
                                refreshToken = "ABC")))

        var result: Token? = null
        whenever(mockPrefs.setSpotifyHostToken()).thenReturn(Action1 { it -> result = it })
        val ts = TestSubscriber<Token>()
        spotifyClient.token("123").subscribe(ts)
        assertNotNull(result)
        assertEquals("ABC", result?.refreshToken)
    }

//    @Test
//    fun playlists() {
//        val ts = TestSubscriber<List<Playlist>>()
//
//        val playlist = arrayListOf(Playlist("123", arrayListOf(Image("123")), "123", "123"))
//        val playlistResponse = PlaylistResponse(playlist)
//        val playlistObservable = mock<Observable<PlaylistResponse>>()
//
//        whenever(playlistObservable.map(any<Func1<in PlaylistResponse, out List<Playlist>>>())).thenReturn(Observable.just(playlist))
//        whenever(mockService.getPlaylists(any())).thenReturn(playlistObservable)
//
//        spotifyClient.playlists().subscribe(ts)
//        ts.assertReceivedOnNext(arrayListOf(playlistResponse.items))
//    }
}