package com.malpo.potluck.networking

import android.util.Base64
import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Token
import com.metova.flyingsaucer.util.PreferenceStore
import com.squareup.moshi.Moshi
import okhttp3.*

class SpotifyGuestAuthenticator(private val client: OkHttpClient,
                                private val moshi: Moshi,
                                private val prefs: PreferenceStore) : Authenticator {

    private var token: String = ""

    companion object {
        val ENCODED_CREDS: String = Base64.encodeToString(("${SpotifyCreds.ID}:${SpotifyCreds.SECRET}")
                .toByteArray(), Base64.DEFAULT)
                .replace("\n", "")
    }

    override fun authenticate(route: Route, response: Response): Request {
        val request = Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .header("Accept", "application/json")
                .header("Authorization", "Basic $ENCODED_CREDS")
                .post(FormBody.Builder()
                        .add("grant_type", "client_credentials")
                        .build())
                .build()

        val tokenCall = client.newCall(request).execute()

        if (tokenCall.isSuccessful) {
            val accessTokenResponse = moshi.adapter(Token::class.java).fromJson(tokenCall.body().string())
            if (accessTokenResponse != null) {
                token = accessTokenResponse.accessToken
                prefs.setSpotifyGuestToken(token)
            }
        }

        return response.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
    }
}