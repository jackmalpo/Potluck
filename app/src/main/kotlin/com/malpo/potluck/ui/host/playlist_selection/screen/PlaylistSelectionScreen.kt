package com.malpo.potluck.ui.host.playlist_selection.screen

import com.malpo.potluck.models.spotify.Playlist
import com.malpo.potluck.ui.screen.ScreenPresenter
import com.malpo.potluck.ui.screen.ScreenView
import dagger.Module
import dagger.Provides
import io.reactivex.Observable

interface PlaylistSelectionScreen {

    @Module
    class PresenterModule {
        @Provides
        internal fun presenter(presenter: PlaylistSelectionPresenter): PlaylistSelectionScreen.Presenter = presenter
    }

    interface Presenter : ScreenPresenter<View, Presenter> {
        fun hostSpotifyPlaylists() : Observable<List<Playlist>>
    }
    interface View : ScreenView<View, Presenter> {}

}