package com.malpo.potluck.networking

import android.util.Base64
import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Token
import rx.Observable
import java.util.*
import javax.inject.Singleton

@Singleton
class SpotifyClient(val service: SpotifyService) {

    val encodedCreds: String = Base64.encodeToString(("${SpotifyCreds.ID}:${SpotifyCreds.SECRET}").toByteArray(), Base64.DEFAULT).replace("\n", "")

    //use when don't need user to login
    fun getAnonToken(): Observable<Token> {
        return service.getAnonToken("client_credentials", "Basic $encodedCreds")
    }

    //use spotify sdk instead to handle this stuff
    fun authorize(): Observable<Token> {
        val params = HashMap<String, String>()
        params.put("client_id", SpotifyCreds.ID)
        params.put("response_type", "code")
        params.put("redirect_uri", "com.malpo.potluck://login")
        params.put("state", encodedCreds)
        return service.authorize(params)
    }

}
