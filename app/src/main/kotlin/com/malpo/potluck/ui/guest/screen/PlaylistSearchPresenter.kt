package com.malpo.potluck.ui.guest.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.misc.toNothing
import com.malpo.potluck.networking.spotify.guest.SpotifyGuestClient
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.tie
import rx.schedulers.Schedulers
import javax.inject.Inject

class PlaylistSearchPresenter
@Inject constructor(val client: SpotifyGuestClient) : PlaylistSearchScreen.Presenter {
    override fun bind(holder: ScreenHolder, x: PlaylistSearchScreen.View, knots: MutableCollection<Knot<*>>) {
        knots.tie(
                client.guestToken().subscribeOn(Schedulers.io()).toNothing()
        )
    }
}
