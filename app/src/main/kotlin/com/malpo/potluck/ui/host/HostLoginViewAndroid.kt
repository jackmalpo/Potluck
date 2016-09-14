package com.malpo.potluck.ui.host

import android.view.View
import android.view.ViewGroup
import com.malpo.potluck.R
import com.malpo.potluck.misc.Knot
import com.malpo.potluck.ui.host.screen.HostLoginScreen
import com.malpo.potluck.ui.host.screen.HostLoginView
import com.malpo.potluck.ui.screen.AndroidScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.metova.slim.Slim
import com.metova.slim.annotation.Layout

@Layout(R.layout.host_login_screen)
class HostLoginViewAndroid : HostLoginView(), AndroidScreen {

    private lateinit var view: View

    override fun bind(holder: ScreenHolder, x: HostLoginScreen.Presenter, knots: MutableCollection<Knot<*>>) {
        //noop
    }

    override fun onCreateView(parent: ViewGroup): View {
        view = Slim.createLayout(parent.context, this, parent)
        return view
    }
}