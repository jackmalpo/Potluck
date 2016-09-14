package com.malpo.potluck.ui.guest.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.ui.screen.ScreenHolder
import javax.inject.Inject

open class PlaylistSearchPresenter @Inject constructor() : PlaylistSearchScreen.Presenter {
    override fun bind(holder: ScreenHolder, x: PlaylistSearchScreen.View, knots: MutableCollection<Knot<*>>) {}
}
