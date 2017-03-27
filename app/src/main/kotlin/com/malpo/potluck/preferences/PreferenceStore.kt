package com.malpo.potluck.preferences

import android.content.SharedPreferences
import com.jakewharton.rxrelay2.BehaviorRelay
import com.malpo.potluck.models.spotify.Token
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import io.reactivex.functions.Consumer

import javax.inject.Singleton


@Singleton
class PreferenceStore(val mSharedPreferences: SharedPreferences, val moshi: Moshi) {

    private val guestToken = BehaviorRelay.create<Token>()
    private val guestLoggedIn = BehaviorRelay.create<Boolean>()
    private val hostLoggedIn = BehaviorRelay.create<Boolean>()
    private val hostToken = BehaviorRelay.create<Token>()
    private val tokenAdapter = moshi.adapter(Token::class.java)

    fun guestLoggedIn(): Observable<Boolean> {
        guestLoggedIn.accept(hasHostToken(_spotifyGuestToken()))
        return guestLoggedIn
    }

    fun spotifyGuestToken(): Observable<Token> {
        guestToken.accept(_spotifyGuestToken())
        return guestToken
    }

    fun setSpotifyGuestToken(): Consumer<Token> {
        return Consumer { guest ->
            mSharedPreferences.edit().putString(SPOTIFY_GUEST_TOKEN, tokenAdapter.toJson(guest)).commit()
            guestToken.accept(guest)
            guestLoggedIn.accept(hasHostToken(guest))
        }
    }

    fun hostLoggedIn(): Observable<Boolean> {
        return spotifyHostToken().flatMap { Observable.just(hasHostToken(it)) }
    }

    fun spotifyHostToken(): Observable<Token> {
        hostToken.accept(_spotifyHostToken())
        return hostToken
    }

    fun setSpotifyHostToken(): Consumer<Token> {
        return Consumer { host ->
            val editor = mSharedPreferences.edit()
            if (host.refreshToken.isNotBlank()) editor.putString(SPOTIFY_HOST_REFRESH, host.refreshToken)
            editor.putString(SPOTIFY_HOST_TOKEN, tokenAdapter.toJson(host)).commit()
            hostToken.accept(host)
            hostLoggedIn.accept(hasHostToken(host))
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

    private fun hasHostToken(token: Token): Boolean {
        return token.accessToken.isNotEmpty()
    }

    companion object {
        val SPOTIFY_GUEST_TOKEN = "spotify_guest_token"
        val SPOTIFY_HOST_TOKEN = "spotify_host_token"
        val SPOTIFY_HOST_REFRESH = "spotify_host_refresh"
    }
}
