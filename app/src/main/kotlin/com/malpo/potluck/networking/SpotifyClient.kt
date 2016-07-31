package com.malpo.potluck.networking

import android.util.Base64
import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.Token
import rx.Observable
import javax.inject.Singleton

@Singleton
class SpotifyClient(val service: SpotifyService) {
    fun getToken() : Observable<Token>{
        val creds = Base64.encodeToString(("${SpotifyCreds.ID}:${SpotifyCreds.SECRET}").toByteArray(), Base64.DEFAULT).replace("\n", "")
        return service.getToken("client_credentials", "Basic $creds")
    }
}
