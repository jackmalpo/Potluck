package com.malpo.potluck

import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever

class MockPreferenceUtil {
    companion object {
        fun setupPreferenceMocks(mockPrefs: SharedPreferences, mockEditor: SharedPreferences.Editor) {
            whenever(mockEditor.clear()).thenReturn(mockEditor)
            whenever(mockEditor.putString(any(), any())).thenReturn(mockEditor)
            whenever(mockPrefs.edit()).thenReturn(mockEditor)
        }
    }
}
