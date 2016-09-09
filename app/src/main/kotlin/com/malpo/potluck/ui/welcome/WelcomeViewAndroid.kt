package com.malpo.potluck.ui.welcome

import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import com.malpo.potluck.R
import com.malpo.potluck.ui.screen.AndroidScreen
import com.malpo.potluck.ui.screen.Knot
import com.malpo.potluck.ui.screen.Knot.Companion.tie
import com.malpo.potluck.ui.screen.wrap
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen
import com.malpo.potluck.ui.welcome.screen.WelcomeView
import com.metova.slim.Slim
import com.metova.slim.annotation.Layout
import kotlinx.android.synthetic.main.welcome_screen.view.*

@Layout(R.layout.welcome_screen)
class WelcomeViewAndroid : WelcomeView(), AndroidScreen {

    private lateinit var view: View

    override fun bind(knots: MutableCollection<Knot<*>>, x: WelcomeScreen.Presenter) {
        knots.wrap(tie(view.host_button.clicks(), hostClicks), tie(view.guest_button.clicks(), guestClicks))
    }

    override fun onCreateView(parent: ViewGroup): View {
        view = Slim.createLayout(parent.context, this, parent)
        return view
    }
}