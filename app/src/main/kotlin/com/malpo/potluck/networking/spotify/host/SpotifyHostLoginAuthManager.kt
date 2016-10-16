package com.malpo.potluck.networking.spotify.host

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.jakewharton.rxrelay.BehaviorRelay
import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyClient
import com.malpo.potluck.preferences.PreferenceStore
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import rx.Observable
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyHostLoginAuthManager @Inject constructor(private val prefs: PreferenceStore,
                                                      private val client: SpotifyClient) {

    lateinit var context: Context

    var tokenRelay = BehaviorRelay.create<Token>()

    fun init(activity: Activity) {
        context = activity

        val builder = AuthenticationRequest.Builder(SpotifyCreds.ID,
                AuthenticationResponse.Type.CODE,
                SpotifyCreds.REDIRECT_URI)
        builder.setScopes(arrayOf("playlist-read-private", "playlist-read-collaborative", "playlist-modify-public", "playlist-modify-private"))
        val request = builder.build()

        AuthenticationClient.openLoginActivity(activity, SpotifyCreds.REQUEST_CODE, request)
    }

    fun handleLoginResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode === SpotifyCreds.REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            if (response.type === AuthenticationResponse.Type.CODE) {
                client.hostToken(response.code)
                        .subscribeOn(Schedulers.io())
                        .subscribe({ tokenRelay.call(it) }, { Timber.e(it) })
            }
        }
    }

    fun tokenResult(): Observable<Token> {
        return tokenRelay
    }

    fun logout() {
//        AuthenticationClient.clearCookies(context)
//        prefs.setSpotifyGuestToken().call("")
    }
}