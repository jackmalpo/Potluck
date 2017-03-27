package com.malpo.potluck.ui.guest.screen

import com.malpo.potluck.knot.Knot
import com.malpo.potluck.knot.to
import com.malpo.potluck.knot.solo
import com.malpo.potluck.networking.spotify.guest.SpotifyGuestClient
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.tie
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.toMaybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PlaylistSearchPresenter
@Inject constructor(val client: SpotifyGuestClient) : PlaylistSearchScreen.Presenter {
    override fun bind(holder: ScreenHolder, x: PlaylistSearchScreen.View, knots: MutableCollection<Knot<*, *>>) {
        knots.tie(
                client.guestToken().toFlowable(BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).solo()
        )
    }
}
