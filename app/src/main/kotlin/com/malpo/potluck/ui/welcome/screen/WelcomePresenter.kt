package com.malpo.potluck.ui.welcome.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.misc.to
import com.malpo.potluck.ui.Page
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.tie
import javax.inject.Inject

class WelcomePresenter @Inject constructor() : WelcomeScreen.Presenter {

    override fun bind(holder: ScreenHolder, x: WelcomeScreen.View, knots: MutableCollection<Knot<*>>) {
        knots.tie(
                x.hostClicks to { holder.goTo(Page.HOST.value) },
                x.guestClicks to { holder.goTo(Page.GUEST.value) }
        )
    }
}
