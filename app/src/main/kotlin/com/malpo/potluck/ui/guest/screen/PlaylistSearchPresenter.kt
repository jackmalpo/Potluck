package com.malpo.potluck.ui.guest.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.misc.Knot.Companion.tie
import com.malpo.potluck.networking.spotify.guest.SpotifyGuestClient
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.wrap
import rx.schedulers.Schedulers
import javax.inject.Inject

open class PlaylistSearchPresenter
@Inject constructor(val guestClient: SpotifyGuestClient) : PlaylistSearchScreen.Presenter {
    override fun bind(holder: ScreenHolder, x: PlaylistSearchScreen.View, knots: MutableCollection<Knot<*>>) {
        knots.wrap(
                tie(guestClient.getAnonToken().subscribeOn(Schedulers.io()))
        )
    }
}
