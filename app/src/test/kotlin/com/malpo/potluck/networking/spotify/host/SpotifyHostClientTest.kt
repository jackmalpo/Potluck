package com.malpo.potluck.networking.spotify.host

import com.malpo.potluck.BaseDaggerTest
import com.malpo.potluck.models.spotify.Image
import com.malpo.potluck.models.spotify.Playlist
import com.malpo.potluck.models.spotify.PlaylistResponse
import com.malpo.potluck.models.spotify.Token
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.observers.TestSubscriber
import kotlin.test.assertEquals

class SpotifyHostClientTest : BaseDaggerTest() {

    lateinit var spotifyClient: SpotifyHostClient

    @Before fun setup() {
        spotifyClient = SpotifyHostClient(testComponent.host(), testComponent.token(), mockPrefStore())
        whenever(mockPrefStore()._spotifyHostToken()).thenReturn(Token(accessToken = "123"))
    }

    @Test fun playlists_retrievesPlaylists() {
        val listOfPlaylist = listOf(Playlist("1", listOf(Image("123")), "Hey", "123"))

        mockWebServer().queueSuccessfulResponse(moshi().adapter(PlaylistResponse::class.java)
                .toJson(PlaylistResponse(listOfPlaylist)))

        val ts = TestSubscriber<List<Playlist>>()
        spotifyClient.playlists().subscribe(ts)
        ts.assertReceivedOnNext(listOf(listOfPlaylist))

        assertEquals("/v1/me/playlists", mockWebServer().takeRequest().path)
    }

    @Test fun playlists_reauthorizesOn401Response() {
        val listOfPlaylist = listOf(Playlist("1", listOf(Image("123")), "Hey", "123"))

        mockWebServer().queueAuthFailure(moshi().adapter(PlaylistResponse::class.java)
                .toJson(PlaylistResponse(listOfPlaylist)))

        mockWebServer().queueSuccessfulResponse(moshi().adapter(Token::class.java)
                .toJson(Token(accessToken = "123")))

        mockWebServer().queueSuccessfulResponse(moshi().adapter(PlaylistResponse::class.java)
                .toJson(PlaylistResponse(listOfPlaylist)))

        val ts = TestSubscriber<List<Playlist>>()
        spotifyClient.playlists().subscribe(ts)

        assertEquals("/v1/me/playlists", mockWebServer().takeRequest().path)
    }
}