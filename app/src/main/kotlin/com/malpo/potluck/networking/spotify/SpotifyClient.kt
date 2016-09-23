package com.malpo.potluck.networking.spotify

import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.models.spotify.Track
import com.malpo.potluck.models.spotify.TrackObject
import com.malpo.potluck.preferences.PreferenceStore
import rx.Observable
import java.util.*
import javax.inject.Singleton

@Singleton
class SpotifyClient(private val service: SpotifyService,
                    private val prefs: PreferenceStore){

    //use when don't need user to login
    fun getGuestToken(): Observable<Token> {
        return service.getGuestToken("Basic ${SpotifyCreds.ENCODED_CREDS}", "client_credentials")
                .doOnNext {
                    prefs.setSpotifyGuestToken().call(it.accessToken)
                }
    }

    fun searchTrack(query: String): Observable<List<Track>> {
        val params = HashMap<String, String>()
        params.put("q", query)
        params.put("type", "track")
        return service.searchTrack("Bearer ${prefs.spotifyGuestToken()}", params)
                .map { it: TrackObject -> it.items.tracks }
    }
}
