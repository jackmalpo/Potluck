package com.malpo.potluck.networking.spotify.host

import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.preferences.PreferenceStore
import com.squareup.moshi.Moshi
import okhttp3.*
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class SpotifyHostAuthenticator(private val client: OkHttpClient,
                               private val tokenBaseUrl: String,
                               private val moshi: Moshi,
                               private val prefs: PreferenceStore) : Authenticator {

    private var token: String = ""

    override fun authenticate(route: Route, response: Response): Request? {
        Timber.d("Host request failed... attempting authorization")

        val request = Request.Builder()
                .url("$tokenBaseUrl/api/token")
                .header("Accept", "application/json")
                .header("Authorization", "Basic ${SpotifyCreds.ENCODED_CREDS}")
                .post(FormBody.Builder()
                        .add("grant_type", "refresh_token")
                        .add("refresh_token", prefs._spotifyHostRefreshToken())
                        .build())
                .build()

        val tokenCall = client.newCall(request).execute()

        if (tokenCall.isSuccessful) {
            val accessTokenResponse = moshi.adapter(Token::class.java).fromJson(tokenCall.body().string())
            if (accessTokenResponse != null) {
                token = accessTokenResponse.accessToken
                prefs.setSpotifyHostToken().call(accessTokenResponse)
            }

            Timber.d("Host auth request successful, retrying previous request with new token")
            return response.request().newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
        } else {
            Timber.d("Host auth attempt failed")
        }

        return null
    }
}