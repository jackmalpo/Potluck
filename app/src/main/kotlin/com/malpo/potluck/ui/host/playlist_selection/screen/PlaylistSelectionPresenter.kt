package com.malpo.potluck.ui.host.playlist_selection.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.misc.to
import com.malpo.potluck.models.spotify.Playlist
import com.malpo.potluck.networking.spotify.host.SpotifyHostClient
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.tie
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PlaylistSelectionPresenter @Inject constructor(val client: SpotifyHostClient) : PlaylistSelectionScreen.Presenter {

    override fun bind(holder: ScreenHolder, x: PlaylistSelectionScreen.View,
                      knots: MutableCollection<Knot<*>>) {
        knots.tie(
                hostSpotifyPlaylists()
                        to { Observable.fromIterable(it).forEach { playlist -> Timber.d(playlist.name) } }
        )

    }

    override fun hostSpotifyPlaylists(): Observable<List<Playlist>> =
            client.playlists().subscribeOn(Schedulers.io())
}
