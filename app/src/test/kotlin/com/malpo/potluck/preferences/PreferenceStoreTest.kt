package com.malpo.potluck.preferences

import android.content.SharedPreferences
import com.malpo.potluck.MockPreferenceUtil
import com.malpo.potluck.models.spotify.Token
import com.nhaarman.mockito_kotlin.*
import com.squareup.moshi.Moshi
import org.junit.Before
import org.junit.Test

class PreferenceStoreTest {
    val mockPrefs: SharedPreferences = mock()
    val mockEditor: SharedPreferences.Editor = mock()
    val moshi: Moshi = Moshi.Builder().build()
    val sampleToken: Token = Token("123", expiresIn = 1, scope = "Shrug", refreshToken = "456", token_type = "Normal")
    val sampleTokenString: String = moshi.adapter(Token::class.java).toJson(sampleToken)

    lateinit var preferenceStore: PreferenceStore

    @Before fun setup() {
        preferenceStore = PreferenceStore(mockPrefs, moshi)
        MockPreferenceUtil.setupPreferenceMocks(mockPrefs, mockEditor)
    }

    @Test fun setGuestToken_savesToken() {
        preferenceStore.setSpotifyGuestToken().accept(sampleToken)
        verify(mockEditor).putString(eq(PreferenceStore.SPOTIFY_GUEST_TOKEN), eq(sampleTokenString))
    }

    @Test fun guestToken_onTokenUpdated_emitsObservableOfUpdatedToken() {
        whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
        preferenceStore.spotifyGuestToken().test()
                .assertValue(sampleToken)
        verify(mockPrefs).getString(eq(PreferenceStore.SPOTIFY_GUEST_TOKEN), any())
    }

    @Test fun guestLoggedIn_onTokenSaved_updatesLoginState() {
        whenever(mockPrefs.getBoolean(any(), any())).thenReturn(false)
        val observer = preferenceStore.guestLoggedIn().test()
        preferenceStore.setSpotifyGuestToken().accept(sampleToken)
        observer.assertValues(false, true)
    }

    @Test fun savesHostToken() {
        preferenceStore.setSpotifyHostToken().accept(sampleToken)
        verify(mockEditor).putString(eq(PreferenceStore.SPOTIFY_HOST_TOKEN), eq(sampleTokenString))
    }

    @Test fun hostToken_onSaveToken_savesRefreshToken() {
        preferenceStore.setSpotifyHostToken().accept(sampleToken)
        verify(mockEditor).putString(PreferenceStore.Companion.SPOTIFY_HOST_REFRESH, sampleToken.refreshToken)
    }

    @Test fun hostLoggedIn_onTokenSaved_updatesLoginState() {
        whenever(mockPrefs.getBoolean(any(), any())).thenReturn(false)
        val observer = preferenceStore.hostLoggedIn().test()
        preferenceStore.setSpotifyHostToken().accept(sampleToken)
        observer.assertValues(false, true)
    }

    @Test fun hostToken_onTokenUpdated_emitsObservableOfUpdatedToken() {
        whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
        preferenceStore.spotifyHostToken().test()
                .assertValue(sampleToken)
        verify(mockPrefs).getString(eq(PreferenceStore.SPOTIFY_HOST_TOKEN), any())
    }

    @Test fun returnsHostRefreshToken() {
        preferenceStore._spotifyHostRefreshToken()
        verify(mockPrefs).getString(eq(PreferenceStore.SPOTIFY_HOST_REFRESH), any())
    }

    @Test fun onHostLoggedIn_emitsObservableOfLoginState() {
        whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
        preferenceStore.hostLoggedIn().test().assertValue(true)
    }

    @Test fun onGuestLoggedIn_emitsObservableOfLoginState() {
        whenever(mockPrefs.getString(any(), any())).thenReturn(sampleTokenString)
        preferenceStore.guestLoggedIn().test().assertValue(true)
    }
}