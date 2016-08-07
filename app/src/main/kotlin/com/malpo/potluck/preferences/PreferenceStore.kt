package com.metova.flyingsaucer.util

import android.content.SharedPreferences
import javax.inject.Singleton


@Singleton
class PreferenceStore(internal var mSharedPreferences: SharedPreferences) {

    fun clearSharedPrefs() {
        mSharedPreferences.edit().clear().apply()
    }

    fun setSpotifyGuestToken(token: String){
        mSharedPreferences.edit().putString(SPOTIFY_GUEST_TOKEN, token).commit()
    }


    fun getSpotifyGuestToken() : String {
        return mSharedPreferences.getString(SPOTIFY_GUEST_TOKEN, "")
    }

    companion object {
        val SPOTIFY_GUEST_TOKEN = "spotify_guest_token"
    }
}
