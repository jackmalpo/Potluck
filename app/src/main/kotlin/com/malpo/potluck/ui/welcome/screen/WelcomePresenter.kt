package com.malpo.potluck.ui.welcome.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.misc.Knot.Companion.tie
import com.malpo.potluck.ui.Page
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.wrap
import javax.inject.Inject

class WelcomePresenter @Inject constructor() : WelcomeScreen.Presenter {

    override fun bind(holder: ScreenHolder, x: WelcomeScreen.View, knots: MutableCollection<Knot<*>>) {
        knots.wrap(
                tie((x.hostClicks), { holder.goTo(Page.HOST.value) }),
                tie((x.guestClicks), { holder.goTo(Page.GUEST.value)}))
    }
}
