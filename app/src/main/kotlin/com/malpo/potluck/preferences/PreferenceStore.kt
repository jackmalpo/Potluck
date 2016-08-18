package com.metova.flyingsaucer.util

import android.content.SharedPreferences
import javax.inject.Singleton


@Singleton
open class PreferenceStore(internal var mSharedPreferences: SharedPreferences) {

    open fun clearSharedPrefs() {
        mSharedPreferences.edit().clear().apply()
    }

    open fun setSpotifyGuestToken(token: String){
        mSharedPreferences.edit().putString(SPOTIFY_GUEST_TOKEN, token).commit()
    }


    fun getSpotifyGuestToken() : String {
        return mSharedPreferences.getString(SPOTIFY_GUEST_TOKEN, "")
    }

    fun clearSpotifyGuestToken() {
        return mSharedPreferences.edit().remove(SPOTIFY_GUEST_TOKEN).apply()
    }

    fun setSpotifyHostToken(token: String){
        mSharedPreferences.edit().putString(SPOTIFY_HOST_TOKEN, token).commit()
    }

    fun getSpotifyHostToken() : String {
        return mSharedPreferences.getString(SPOTIFY_HOST_TOKEN, "")
    }

    fun clearSpotifyHostToken() {
        mSharedPreferences.edit().remove(SPOTIFY_HOST_TOKEN).apply()
    }

    companion object {
        val SPOTIFY_GUEST_TOKEN = "spotify_guest_token"
        val SPOTIFY_HOST_TOKEN = "spotify_host_token"
    }
}
