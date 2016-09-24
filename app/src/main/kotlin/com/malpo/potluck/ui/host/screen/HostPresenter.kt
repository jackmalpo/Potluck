package com.malpo.potluck.ui.host.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.ui.screen.ScreenHolder
import javax.inject.Inject

open class HostPresenter @Inject constructor() : HostScreen.Presenter {

    override fun bind(holder: ScreenHolder, x: HostScreen.View, knots: MutableCollection<Knot<*>>) {
    }

}
