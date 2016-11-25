package com.malpo.potluck.networking.spotify.host

import com.malpo.potluck.BaseSpekTest
import com.malpo.potluck.models.spotify.Image
import com.malpo.potluck.models.spotify.Playlist
import com.malpo.potluck.models.spotify.PlaylistResponse
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import rx.observers.TestSubscriber

class SpotifyHostClientTest : BaseSpekTest({
    val mockPrefs: PreferenceStore = testComponent.preferenceStore()
    val mockService: SpotifyService = testComponent.hostSpotifyService()
    val mockWebServer: MockWebServer = testComponent.mockWebServer()
    val sampleToken = Token(accessToken = "123", expiresIn = 1, token_type = "thisKind", refreshToken = "ABC")

    describe("a spotify guest client") {
        val spotifyClient = SpotifyHostClient(mockService, mockPrefs)

        beforeEach {
            reset(mockPrefs)
//            val type = Types.newParameterizedType(List::class.java, Token::class.java)
            mockWebServer.enqueue(MockResponse()
                    .setResponseCode(401)
                    .setBody(testComponent.moshi()
                            .adapter(PlaylistResponse::class.java)
                            .toJson(PlaylistResponse(
                                    listOf(Playlist(id = "1",
                                            images = listOf(Image("123")),
                                            name = "Hey", uri = "123"))))))
        }

        it("should retrieve a list of playlists from the api") {
            val ts = TestSubscriber<List<Playlist>>()
            whenever(mockPrefs._spotifyHostToken()).thenReturn(Token(accessToken = "123"))
            spotifyClient.playlists().subscribe(ts)
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