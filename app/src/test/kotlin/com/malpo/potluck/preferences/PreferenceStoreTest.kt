package com.malpo.potluck.preferences

import android.content.SharedPreferences
import com.malpo.potluck.models.spotify.Token
import com.nhaarman.mockito_kotlin.*
import com.squareup.moshi.Moshi
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Before
import org.junit.Test
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.reset
import org.mockito.MockitoAnnotations
import rx.observers.TestSubscriber

@RunWith(JUnitPlatform::class)
class PreferenceStoreTest : Spek({
    val mockPrefs: SharedPreferences = mock()
    val mockEditor: SharedPreferences.Editor = mock()
    val moshi: Moshi = Moshi.Builder().build()
    val sampleToken = Token(accessToken = "123", expiresIn = 1, scope = "Shrug", refreshToken = "456", token_type = "Normal")
    val sampleTokenString = moshi.adapter(Token::class.java).toJson(sampleToken)

    given("A preference store") {
        val preferenceStore = PreferenceStore(mockPrefs, moshi)

        beforeEach {
            reset(mockPrefs, mockEditor)

            whenever(mockEditor.clear()).thenReturn(mockEditor)
            whenever(mockEditor.putString(any(), any())).thenReturn(mockEditor)
            whenever(mockPrefs.edit()).thenReturn(mockEditor)
        }

        it("Should put the string in the SPOTIFY_GUEST_TOKEN SharedPreference") {
            preferenceStore.setSpotifyGuestToken().call(sampleToken)
            verify(mockEditor).putString(eq(PreferenceStore.Companion.SPOTIFY_GUEST_TOKEN), eq(sampleTokenString))
        }

        it("Should emit observables of guest token updates") {
            val ts = TestSubscriber<Token>()
            whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
            preferenceStore.spotifyGuestToken().subscribe(ts)
            verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_GUEST_TOKEN), any())
            ts.assertReceivedOnNext(arrayListOf(sampleToken))
        }

        it("Should save the host token") {
            preferenceStore.setSpotifyHostToken().call(sampleToken)
            verify(mockEditor).putString(eq(PreferenceStore.Companion.SPOTIFY_HOST_TOKEN), eq(sampleTokenString))
        }

        it("Should save the refresh token when the host token is saved") {
            preferenceStore.setSpotifyHostToken().call(sampleToken)
            verify(mockEditor).putString(PreferenceStore.Companion.SPOTIFY_HOST_REFRESH, sampleToken.refreshToken)
        }

        it("Should emit observables of host token updates") {
            val ts = TestSubscriber<Token>()
            whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
            preferenceStore.spotifyHostToken().subscribe(ts)
            verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_HOST_TOKEN), any())
            ts.assertReceivedOnNext(arrayListOf(sampleToken))
        }

        it("Should return the refresh token") {
            preferenceStore._spotifyHostRefreshToken()
            verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_HOST_REFRESH), any())
        }
    }
})