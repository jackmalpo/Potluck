package com.malpo.potluck.networking.spotify.guest

import com.malpo.potluck.di.qualifiers.Guest
import com.malpo.potluck.di.qualifiers.SpotifyToken
import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.models.spotify.Track
import com.malpo.potluck.models.spotify.TrackResponse
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.SpotifyTokenService
import com.malpo.potluck.preferences.PreferenceStore
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyGuestClient @Inject constructor(@Guest private val service: SpotifyService,
                                             @SpotifyToken private val tokenService: SpotifyTokenService,
                                             private val prefs: PreferenceStore) {

    //use when don't need user to login
    fun guestToken(): Observable<Token> {
        return tokenService.guestToken("Basic ${SpotifyCreds.ENCODED_CREDS}", "client_credentials")
                .doOnNext {
                    prefs.setSpotifyGuestToken().accept(it)
                }
    }

    fun searchTrack(query: String): Observable<List<Track>> {
        val params = hashMapOf("q" to query, "type" to "track")
        return service.searchTrack("Bearer ${prefs._spotifyGuestToken().accessToken}", params)
                .map { it: TrackResponse -> it.trackItems.tracks }
    }
}
