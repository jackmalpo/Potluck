package com.malpo.potluck.ui.host.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.ui.screen.ScreenHolder
import javax.inject.Inject

open class HostLoginPresenter @Inject constructor() : HostLoginScreen.Presenter {
    override fun bind(holder: ScreenHolder, x: HostLoginScreen.View, knots: MutableCollection<Knot<*>>) {}
}
