package com.malpo.potluck.preferences

import android.content.SharedPreferences
import com.jakewharton.rxrelay.BehaviorRelay
import com.malpo.potluck.models.spotify.Token
import com.squareup.moshi.Moshi
import rx.Observable
import rx.functions.Action1
import javax.inject.Singleton


@Singleton
class PreferenceStore(val mSharedPreferences: SharedPreferences, val moshi: Moshi) {

    private val guestToken = BehaviorRelay.create<Token>()
    private val hostToken = BehaviorRelay.create<Token>()
    private val tokenAdapter = moshi.adapter(Token::class.java)

    fun spotifyGuestToken(): Observable<Token> {
        guestToken.call(_spotifyGuestToken())
        return guestToken.asObservable()
    }

    fun setSpotifyGuestToken(): Action1<Token> {
        return Action1 { guest ->
            mSharedPreferences.edit().putString(SPOTIFY_GUEST_TOKEN, tokenAdapter.toJson(guest)).commit()
            guestToken.call(guest)
        }
    }

    fun spotifyHostToken(): Observable<Token> {
        hostToken.call(_spotifyHostToken())
        return hostToken.asObservable()
    }

    fun setSpotifyHostToken(): Action1<Token> {
        return Action1 { host ->
            val editor = mSharedPreferences.edit()
            if (host.refreshToken.isNotBlank()) editor.putString(SPOTIFY_HOST_REFRESH, host.refreshToken)
            editor.putString(SPOTIFY_HOST_TOKEN, tokenAdapter.toJson(host)).commit()
            hostToken.call(host)
        }
    }

    fun _spotifyHostRefreshToken(): String {
        return mSharedPreferences.getString(SPOTIFY_HOST_REFRESH, "") ?: ""
    }

    fun _spotifyHostToken(): Token {
        val token = mSharedPreferences.getString(SPOTIFY_HOST_TOKEN, "") ?: ""
        return if (token.isNotBlank()) tokenAdapter.fromJson(token) else Token()
    }

    fun _spotifyGuestToken(): Token {
        val token = mSharedPreferences.getString(SPOTIFY_GUEST_TOKEN, "") ?: ""
        return if (token.isNotBlank()) tokenAdapter.fromJson(token) else Token()
    }

    companion object {
        val SPOTIFY_GUEST_TOKEN = "spotify_guest_token"
        val SPOTIFY_HOST_TOKEN = "spotify_host_token"
        val SPOTIFY_HOST_REFRESH = "spotify_host_refresh"
    }
}
