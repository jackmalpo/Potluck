package com.malpo.potluck.networking.spotify.host

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.preferences.PreferenceStore
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class SpotifyHostAuthenticationManager(private val prefs: PreferenceStore) {

    lateinit var context: Context

    fun init(activity: Activity) {
        context = activity

        val builder = AuthenticationRequest.Builder(SpotifyCreds.ID,
                AuthenticationResponse.Type.TOKEN,
                SpotifyCreds.REDIRECT_URI)
        builder.setScopes(arrayOf("playlist-read-private", "playlist-read-collaborative", "playlist-modify-public", "playlist-modify-private"))
        val request = builder.build()

        AuthenticationClient.openLoginActivity(activity, SpotifyCreds.REQUEST_CODE, request)
    }

    fun handleLoginResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode === SpotifyCreds.REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, intent)
            if (response.type === AuthenticationResponse.Type.TOKEN) {
                Timber.e(response.accessToken)
                prefs.setSpotifyHostToken(response.accessToken)
            }
        }
    }

    fun logout() {
        AuthenticationClient.clearCookies(context)
        prefs.clearSpotifyHostToken()
    }
}