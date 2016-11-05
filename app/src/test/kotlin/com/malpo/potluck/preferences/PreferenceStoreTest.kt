package com.malpo.potluck.preferences

import android.content.SharedPreferences
import com.malpo.potluck.models.spotify.Token
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.squareup.moshi.Moshi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import rx.observers.TestSubscriber

class PreferenceStoreTest {

    @Mock
    lateinit var mockPrefs: SharedPreferences

    @Mock
    lateinit var mockEditor: SharedPreferences.Editor

    lateinit var preferenceStore: PreferenceStore

    val moshi: Moshi = Moshi.Builder().build()

    val sampleToken = Token(accessToken = "123", expiresIn = 1, scope = "Shrug", refreshToken = "456", token_type = "Normal")

    val sampleTokenString = moshi.adapter(Token::class.java).toJson(sampleToken)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        whenever(mockEditor.clear()).thenReturn(mockEditor)
        whenever(mockEditor.putString(any(), any())).thenReturn(mockEditor)
        whenever(mockPrefs.edit()).thenReturn(mockEditor)

        preferenceStore = PreferenceStore(mockPrefs, moshi)
    }

    @Test
    fun setSpotifyGuestToken() {
        preferenceStore.setSpotifyGuestToken().call(sampleToken)
        verify(mockEditor).putString(eq(PreferenceStore.Companion.SPOTIFY_GUEST_TOKEN), eq(sampleTokenString))
    }

    @Test
    fun spotifyGuestToken() {
        val ts = TestSubscriber<Token>()
        whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
        preferenceStore.spotifyGuestToken().subscribe(ts)
        verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_GUEST_TOKEN), any())
        ts.assertReceivedOnNext(arrayListOf(sampleToken))
    }

    @Test
    fun setSpotifyHostToken_savesHostToken() {
        preferenceStore.setSpotifyHostToken().call(sampleToken)
        verify(mockEditor).putString(eq(PreferenceStore.Companion.SPOTIFY_HOST_TOKEN), eq(sampleTokenString))
    }

    @Test
    fun setSpotifyHostToken_savesRefreshToken() {
        preferenceStore.setSpotifyHostToken().call(sampleToken)
        verify(mockEditor).putString(eq(PreferenceStore.Companion.SPOTIFY_HOST_REFRESH), eq(sampleToken.refreshToken))
    }

    @Test
    fun spotifyHostToken() {
        val ts = TestSubscriber<Token>()
        whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
        preferenceStore.spotifyHostToken().subscribe(ts)
        verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_HOST_TOKEN), any())
        ts.assertReceivedOnNext(arrayListOf(sampleToken))
    }

    @Test
    fun getSpotifyHostRefreshToken() {
        preferenceStore._spotifyHostRefreshToken()
        verify(mockPrefs).getString(eq(PreferenceStore.Companion.SPOTIFY_HOST_REFRESH), any())
    }
}