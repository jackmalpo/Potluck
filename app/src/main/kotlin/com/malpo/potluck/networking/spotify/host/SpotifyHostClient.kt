package com.malpo.potluck.networking.spotify.host

import com.malpo.potluck.di.qualifiers.Host
import com.malpo.potluck.di.qualifiers.SpotifyToken
import com.malpo.potluck.models.SpotifyCreds
import com.malpo.potluck.models.spotify.Playlist
import com.malpo.potluck.models.spotify.PlaylistResponse
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.SpotifyTokenService
import com.malpo.potluck.preferences.PreferenceStore
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyHostClient @Inject constructor(@Host private val hostService: SpotifyService,
                                            @SpotifyToken private val tokenService: SpotifyTokenService,
                                            private val prefs: PreferenceStore) {

    fun token(code: String): Observable<Token> {
        return tokenService.hostToken("Basic ${SpotifyCreds.ENCODED_CREDS}", "authorization_code", code,
                "com.malpo.potluck://login")
                .doOnNext {
                    prefs.setSpotifyHostToken().accept(it)
                }
    }

    fun playlists(): Observable<List<Playlist>> {
        return hostService.getPlaylists("Bearer ${prefs._spotifyHostToken().accessToken}")
                .map(PlaylistResponse::items)
    }
}