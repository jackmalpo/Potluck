package com.malpo.potluck.ui.welcome.screen

import com.malpo.potluck.ui.screen.Knot
import com.malpo.potluck.ui.screen.Knot.Companion.tie
import com.malpo.potluck.ui.screen.wrap
import timber.log.Timber
import javax.inject.Inject

open class WelcomePresenter @Inject constructor() : WelcomeScreen.Presenter {

    override fun bind(knots: MutableCollection<Knot<*>>, x: WelcomeScreen.View) {
        knots.wrap(tie((x.hostClicks), { Timber.d("host") }), tie((x.guestClicks), { Timber.d("guest") }))
    }
}
