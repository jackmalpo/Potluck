package com.malpo.potluck.networking.spotify.guest

import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.models.spotify.Track
import com.malpo.potluck.models.spotify.TrackObject
import com.malpo.potluck.preferences.PreferenceStore
import rx.Observable
import java.util.*
import javax.inject.Singleton

@Singleton
class SpotifyGuestClient(private val service: SpotifyGuestService,
                         private val prefs : PreferenceStore) {

    //use when don't need user to login
    fun getAnonToken(): Observable<Token> {
        return service.getAnonToken("Basic ${SpotifyCreds.ENCODED_CREDS}", "client_credentials")
                .doOnNext {
                    prefs.setSpotifyGuestToken(it.accessToken)
                }
    }

    fun search(query: String): Observable<List<Track>> {
        val params = HashMap<String, String>()
        params.put("q", query)
        params.put("type", "track")
        return service.searchTrack("Bearer ${prefs.getSpotifyGuestToken()}", params)
                .map { it: TrackObject -> it.items.tracks }
    }
}
