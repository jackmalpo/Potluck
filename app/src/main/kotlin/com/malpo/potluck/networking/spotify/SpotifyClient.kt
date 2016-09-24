package com.malpo.potluck.networking.spotify

import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.*
import com.malpo.potluck.preferences.PreferenceStore
import rx.Observable
import java.util.*
import javax.inject.Singleton

@Singleton
class SpotifyClient(private val service: SpotifyService,
                    private val prefs: PreferenceStore) {

    private val bearerHeader = "Bearer ${prefs.spotifyGuestToken()}"
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
        return service.searchTrack("Bearer ${prefs._spotifyGuestToken()}", params)
                .map { it: TrackResponse -> it.trackItems.tracks }
    }

    fun getPlaylists(): Observable<List<Playlist>> {
        return service.getPlaylists("Bearer ${prefs._spotifyHostToken()}")
                .map(PlaylistResponse::items)
    }
}
