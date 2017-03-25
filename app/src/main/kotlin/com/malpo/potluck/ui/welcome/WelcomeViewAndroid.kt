package com.malpo.potluck.ui.welcome

import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.malpo.potluck.R
import com.malpo.potluck.misc.Knot
import com.malpo.potluck.misc.to
import com.malpo.potluck.ui.screen.AndroidScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.tie
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen
import com.malpo.potluck.ui.welcome.screen.WelcomeView
import com.metova.slim.Slim
import com.metova.slim.annotation.Layout
import kotlinx.android.synthetic.main.welcome_screen.view.*

@Layout(R.layout.welcome_screen)
class WelcomeViewAndroid : WelcomeView(), AndroidScreen {

    private lateinit var view: View

    override fun bind(holder: ScreenHolder, x: WelcomeScreen.Presenter, knots: MutableCollection<Knot<*>>) {
        knots.tie(
                view.host_button.clicks() to  hostClicks,
                view.guest_button.clicks() to guestClicks
        )
    }

    override fun onCreateView(parent: ViewGroup): View {
        view = Slim.createLayout(parent.context, this, parent)
        return view
    }
}