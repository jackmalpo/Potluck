package com.malpo.potluck.networking

import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.models.spotify.TrackObject
import com.metova.flyingsaucer.util.PreferenceStore
import rx.Observable
import java.util.*
import javax.inject.Singleton

@Singleton
class SpotifyGuestClient(private val service: SpotifyGuestService,
                         private val prefs : PreferenceStore) {

    //use when don't need user to login
    fun getAnonToken(): Observable<Token> {
        return service.getAnonToken("Basic ${SpotifyGuestAuthenticator.ENCODED_CREDS}", "client_credentials")
                .doOnNext {
                    prefs.setSpotifyGuestToken(it.accessToken)
                }
    }

    fun search(query: String): Observable<Any?> {
        val params = HashMap<String, String>()
        params.put("q", query)
        params.put("type", "track")
//        return service.searchTrack("Bearer ${prefs.getSpotifyGuestToken()}", params)
        return service.searchTrack("Bearer BQD3xRK26SGuCKsl5QHE29HYK9I74DfnOqPGZPEfA337NWavvkk18mCvmOi0o4JpAwJ5Rcvk8NCTk4hb4MW6BOjuoa4", params)
                .map { it: TrackObject -> it.items.tracks }
    }
}
