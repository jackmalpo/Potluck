package com.malpo.potluck.networking.spotify.host

import com.malpo.potluck.BaseSpekTest
import com.malpo.potluck.models.spotify.Image
import com.malpo.potluck.models.spotify.Playlist
import com.malpo.potluck.models.spotify.PlaylistResponse
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.queueAuthFailure
import com.malpo.potluck.queueSuccessfulResponse
import com.nhaarman.mockito_kotlin.whenever
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import rx.observers.TestSubscriber
import kotlin.test.assertEquals


class SpotifyHostClientTest : BaseSpekTest({

    describe("a spotify guest client") {

        it("should retrieve a list of playlists from the api") {
            SpotifyService.TOKEN_ENDPOINT = "TEST"
            val spotifyClient = SpotifyHostClient(testComponent.getHostSpotifyService(), mockPrefStore())

            whenever(mockPrefStore()._spotifyHostToken()).thenReturn(Token(accessToken = "123"))

            val listOfPlaylist = listOf(Playlist("1", listOf(Image("123")), "Hey", "123"))

            mockWebServer().queueSuccessfulResponse(moshi().adapter(PlaylistResponse::class.java)
                    .toJson(PlaylistResponse(listOfPlaylist)))

            val ts = TestSubscriber<List<Playlist>>()
            spotifyClient.playlists().subscribe(ts)
            ts.assertReceivedOnNext(listOf(listOfPlaylist))

            assertEquals("/v1/me/playlists", mockWebServer().takeRequest().path)
        }

        it("should reauthorize on 401 response") {
            val spotifyClient = SpotifyHostClient(testComponent.getHostSpotifyService(), mockPrefStore())

            whenever(mockPrefStore()._spotifyHostToken()).thenReturn(Token(accessToken = "123"))

            val listOfPlaylist = listOf(Playlist("1", listOf(Image("123")), "Hey", "123"))

            mockWebServer().queueAuthFailure(moshi().adapter(PlaylistResponse::class.java)
                    .toJson(PlaylistResponse(listOfPlaylist)))

            val ts = TestSubscriber<List<Playlist>>()
            spotifyClient.playlists().subscribe(ts)

            assertEquals("/v1/me/playlists", mockWebServer().takeRequest().path)




            ts.assertReceivedOnNext(listOf(listOfPlaylist))
        }

//        it("should retrieve the host token from the api") {
//            spotifyClient.token("123").subscribe()
//        }
//
//        it("should save the host token") {
//            var result: Token? = null
//            whenever(mockPrefs.setSpotifyHostToken()).thenReturn(Action1 { it -> result = it })
//
////            val ts = TestSubscriber<Token>()
//            spotifyClient.token("123").subscribe()
//
//            assertNotNull(result)
//            assertEquals("ABC", result?.refreshToken)
//            assertEquals("123", result?.accessToken)
//            assertEquals(1, result?.expiresIn)
//            assertEquals("thisKind", result?.token_type)
//        }

    }
})