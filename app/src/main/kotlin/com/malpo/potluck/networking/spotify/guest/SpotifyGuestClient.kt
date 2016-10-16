package com.malpo.potluck.networking.spotify.guest

import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.models.spotify.Track
import com.malpo.potluck.models.spotify.TrackResponse
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.preferences.PreferenceStore
import rx.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SpotifyGuestClient @Inject constructor(@Named("guest") private val service: SpotifyService,
                                             private val prefs: PreferenceStore) {

    //use when don't need user to login
    fun guestToken(): Observable<Token> {
        return service.guestToken("Basic ${SpotifyCreds.ENCODED_CREDS}", "client_credentials")
                .doOnNext {
                    prefs.setSpotifyGuestToken().call(it)
                }
    }

    fun searchTrack(query: String): Observable<List<Track>> {
        val params = HashMap<String, String>()
        params.put("q", query)
        params.put("type", "track")
        return service.searchTrack("Bearer ${prefs._spotifyGuestToken().accessToken}", params)
                .map { it: TrackResponse -> it.trackItems.tracks }
    }
}
