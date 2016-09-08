package com.malpo.potluck.ui.welcome

import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.clicks
import com.malpo.potluck.R
import com.malpo.potluck.ui.mvp.AndroidScreen
import com.malpo.potluck.ui.mvp.Knot
import com.malpo.potluck.ui.mvp.Knot.Companion.tie
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen
import com.malpo.potluck.ui.welcome.screen.WelcomeView
import com.metova.slim.Slim
import com.metova.slim.annotation.Layout
import kotlinx.android.synthetic.main.welcome_screen.view.*

@Layout(R.layout.welcome_screen)
class WelcomeViewAndroid : WelcomeView(), AndroidScreen {

    private lateinit var view: View

    private lateinit var testView: Knot<Unit>

    override fun bind(elements: MutableCollection<Knot<*>>, x: WelcomeScreen.Presenter) {
        super.bind(elements, x)
        elements.addAll(mutableListOf(
                tie(view.host_button.clicks(), hostClicks),
                tie(view.guest_button.clicks(), guestClicks)
        ))
    }

    override fun onCreateView(parent: ViewGroup): View {
        view = Slim.createLayout(parent.context, this, parent)
        return view
    }

    override fun onViewCreated(view: View) {
        //noop
    }

    override fun onDestroyView() {
        //noop
    }
}