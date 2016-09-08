package com.malpo.potluck.ui.welcome.screen

import com.malpo.potluck.ui.mvp.Knot
import com.malpo.potluck.ui.mvp.Knot.Companion.tie
import timber.log.Timber
import javax.inject.Inject

open class WelcomePresenter @Inject constructor() : WelcomeScreen.Presenter {

    override fun bind(elements: MutableCollection<Knot<*>>, x: WelcomeScreen.View) {
        elements.addAll(mutableListOf(
                tie(x.hostClicked(), { Timber.d("host") }),
                tie(x.guestClicked(), { Timber.d("guest") })
        )
        )
    }
}
