package com.malpo.potluck.networking.spotify.host

import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Playlist
import com.malpo.potluck.models.spotify.PlaylistResponse
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.preferences.PreferenceStore
import rx.Observable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SpotifyHostClient @Inject constructor(@Named("host") private val service: SpotifyService,
                                            private val prefs: PreferenceStore) {

    fun token(code: String): Observable<Token> {
        return service.hostToken("Basic ${SpotifyCreds.ENCODED_CREDS}", "authorization_code", code, "com.malpo.potluck://login")
                .doOnNext {
                    prefs.setSpotifyHostToken().call(it)
                }
    }

    fun playlists(): Observable<List<Playlist>> {
        return service.getPlaylists("Bearer ${prefs._spotifyHostToken().accessToken}")
                .map(PlaylistResponse::items)
    }
}