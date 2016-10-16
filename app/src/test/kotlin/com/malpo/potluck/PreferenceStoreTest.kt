package com.malpo.potluck

import android.content.SharedPreferences
import com.malpo.potluck.preferences.PreferenceStore
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
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


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        whenever(mockEditor.clear()).thenReturn(mockEditor)
        whenever(mockEditor.putString(any(), any())).thenReturn(mockEditor)
        whenever(mockPrefs.edit()).thenReturn(mockEditor)

        preferenceStore = PreferenceStore(mockPrefs)
    }

    @Test
    fun setSpotifyGuestToken() {
        preferenceStore.setSpotifyGuestToken().call("123")
        verify(mockEditor).putString(PreferenceStore.SPOTIFY_GUEST_TOKEN, "123")
    }

    @Test
    fun spotifyGuestToken() {
        val ts = TestSubscriber<String>()
        whenever(mockPrefs.getString(any(), any())).thenReturn("123")
        preferenceStore.spotifyGuestToken().subscribe(ts)
        verify(mockPrefs).getString(PreferenceStore.SPOTIFY_GUEST_TOKEN, "")
        ts.assertReceivedOnNext(arrayListOf("123"))
    }

    @Test
    fun setSpotifyHostToken() {
        preferenceStore.setSpotifyHostToken().call("123")
        verify(mockEditor).putString(PreferenceStore.SPOTIFY_HOST_TOKEN, "123")
    }

    @Test
    fun spotifyHostToken() {
        val ts = TestSubscriber<String>()
        whenever(mockPrefs.getString(any(), any())).thenReturn("123")
        preferenceStore.spotifyHostToken().subscribe(ts)
        verify(mockPrefs).getString(PreferenceStore.SPOTIFY_HOST_TOKEN, "")
        ts.assertReceivedOnNext(arrayListOf("123"))
    }
}