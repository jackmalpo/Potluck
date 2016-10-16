package com.malpo.potluck.networking.spotify

import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.*
import com.malpo.potluck.preferences.PreferenceStore
import rx.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyClient @Inject constructor(private val service: SpotifyService,
                                        private val prefs: PreferenceStore) {

    //use when don't need user to login
    fun guestToken(): Observable<Token> {
        return service.guestToken("Basic ${SpotifyCreds.ENCODED_CREDS}", "client_credentials")
                .doOnNext {
                    prefs.setSpotifyGuestToken().call(it)
                }
    }

    fun hostToken(code: String): Observable<Token> {
        return service.hostToken("Basic ${SpotifyCreds.ENCODED_CREDS}", "authorization_code", code, "com.malpo.potluck://login")
                .doOnNext {
                    prefs.setSpotifyHostToken().call(it)
                }
    }

    fun searchTrack(query: String): Observable<List<Track>> {
        val params = HashMap<String, String>()
        params.put("q", query)
        params.put("type", "track")
        return service.searchTrack("Bearer ${prefs._spotifyGuestToken().accessToken}", params)
                .map { it: TrackResponse -> it.trackItems.tracks }
    }

    fun playlists(): Observable<List<Playlist>> {
        return service.getPlaylists("Bearer ${prefs._spotifyHostToken().accessToken}")
                .map(PlaylistResponse::items)
    }
}
