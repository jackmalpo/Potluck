package com.malpo.potluck.preferences

import android.content.SharedPreferences
import com.jakewharton.rxrelay.BehaviorRelay
import rx.Observable
import rx.functions.Action1
import javax.inject.Singleton


@Singleton
open class PreferenceStore(internal var mSharedPreferences: SharedPreferences) {

    private val guestToken = BehaviorRelay.create<String>()
    private val hostToken = BehaviorRelay.create<String>()

    fun spotifyGuestToken(): Observable<String> {
        guestToken.call(_spotifyGuestToken())
        return guestToken.asObservable()
    }

    open fun setSpotifyGuestToken(): Action1<String> {
        return Action1 { guest ->
            mSharedPreferences.edit().putString(SPOTIFY_GUEST_TOKEN, guest).commit()
            guestToken.call(guest)
        }
    }

    fun spotifyHostToken(): Observable<String> {
        hostToken.call(_spotifyHostToken())
        return hostToken.asObservable()
    }

    fun setSpotifyHostToken(): Action1<String> {
        return Action1 { host ->
            mSharedPreferences.edit().putString(SPOTIFY_HOST_TOKEN, host).commit()
            hostToken.call(host)
        }
    }

    fun _spotifyHostToken() : String {
        return mSharedPreferences.getString(SPOTIFY_HOST_TOKEN, "")
    }

    fun _spotifyGuestToken() : String {
        return mSharedPreferences.getString(SPOTIFY_GUEST_TOKEN, "")
    }

    companion object {
        val SPOTIFY_GUEST_TOKEN = "spotify_guest_token"
        val SPOTIFY_HOST_TOKEN = "spotify_host_token"
    }
}
