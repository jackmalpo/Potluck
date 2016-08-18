package com.malpo.potluck

import android.content.SharedPreferences
import com.metova.flyingsaucer.util.PreferenceStore
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
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
    @Throws(Exception::class)
    fun setSpotifyGuestToken_setsSharedPref() {
        preferenceStore.setSpotifyGuestToken("123")
        verify(mockEditor).putString(PreferenceStore.SPOTIFY_GUEST_TOKEN, "123")
    }
}