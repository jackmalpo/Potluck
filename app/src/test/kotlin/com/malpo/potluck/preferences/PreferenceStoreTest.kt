package com.malpo.potluck.preferences

import android.content.SharedPreferences
import com.malpo.potluck.BaseSpekTest
import com.malpo.potluck.MockPreferenceUtil.Companion.setupPreferenceMocks
import com.malpo.potluck.models.spotify.Token
import com.nhaarman.mockito_kotlin.*
import com.squareup.moshi.Moshi
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.reset
import rx.observers.TestSubscriber

@RunWith(JUnitPlatform::class)
class PreferenceStoreTest : BaseSpekTest({
    val mockPrefs: SharedPreferences = mock()
    val mockEditor: SharedPreferences.Editor = mock()
    val moshi: Moshi = testComponent.moshi()
    val sampleToken = Token(accessToken = "123", expiresIn = 1, scope = "Shrug", refreshToken = "456", token_type = "Normal")
    val sampleTokenString = moshi.adapter(Token::class.java).toJson(sampleToken)

    describe("a preference store") {
        val preferenceStore = PreferenceStore(mockPrefs, moshi)

        beforeEach {
            reset(mockPrefs, mockEditor)
            setupPreferenceMocks(mockPrefs, mockEditor)
        }

        it("should save the guest token") {
            preferenceStore.setSpotifyGuestToken().call(sampleToken)
            verify(mockEditor).putString(eq(PreferenceStore.Companion.SPOTIFY_GUEST_TOKEN), eq(sampleTokenString))
        }

        it("should emit observables of guest token updates") {
            val ts = TestSubscriber<Token>()
            whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
            preferenceStore.spotifyGuestToken().subscribe(ts)
            verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_GUEST_TOKEN), any())
            ts.assertReceivedOnNext(arrayListOf(sampleToken))
        }

        it("should update the guest login state when the guest token is saved") {
            whenever(mockPrefs.getBoolean(any(), any())).thenReturn(false)
            val ts = TestSubscriber<Boolean>()
            preferenceStore.guestLoggedIn().subscribe(ts)
            preferenceStore.setSpotifyGuestToken().call(sampleToken)
            ts.assertReceivedOnNext(arrayListOf(false, true))
        }

        it("should save the host token") {
            preferenceStore.setSpotifyHostToken().call(sampleToken)
            verify(mockEditor).putString(eq(PreferenceStore.Companion.SPOTIFY_HOST_TOKEN), eq(sampleTokenString))
        }

        it("should save the refresh token when the host token is saved") {
            preferenceStore.setSpotifyHostToken().call(sampleToken)
            verify(mockEditor).putString(PreferenceStore.Companion.SPOTIFY_HOST_REFRESH, sampleToken.refreshToken)
        }

        it("should update the host login state when the host token is saved") {
            whenever(mockPrefs.getBoolean(any(), any())).thenReturn(false)
            val ts = TestSubscriber<Boolean>()
            preferenceStore.hostLoggedIn().subscribe(ts)
            preferenceStore.setSpotifyHostToken().call(sampleToken)
            ts.assertReceivedOnNext(arrayListOf(false, true))
        }

        it("should emit observables of host token updates") {
            val ts = TestSubscriber<Token>()
            whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
            preferenceStore.spotifyHostToken().subscribe(ts)
            verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_HOST_TOKEN), any())
            ts.assertReceivedOnNext(arrayListOf(sampleToken))
        }

        it("should return the host refresh token") {
            preferenceStore._spotifyHostRefreshToken()
            verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_HOST_REFRESH), any())
        }

        it("should emit observables of whether or not the host is logged in") {
            val ts = TestSubscriber<Boolean>()
            whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
            preferenceStore.hostLoggedIn().subscribe(ts)
            ts.assertReceivedOnNext(arrayListOf(true))
        }

        it("should emit observables of whether or not the guest is logged in") {
            val ts = TestSubscriber<Boolean>()
            whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
            preferenceStore.guestLoggedIn().subscribe(ts)
            ts.assertReceivedOnNext(arrayListOf(true))
        }
    }
})