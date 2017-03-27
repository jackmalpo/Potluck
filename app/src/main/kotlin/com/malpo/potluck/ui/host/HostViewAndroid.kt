package com.malpo.potluck.ui.host

import android.view.View
import android.view.ViewGroup
import com.malpo.potluck.R
import com.malpo.potluck.knot.Knot
import com.malpo.potluck.ui.host.screen.HostScreen
import com.malpo.potluck.ui.host.screen.HostView
import com.malpo.potluck.ui.screen.AndroidScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.metova.slim.Slim
import com.metova.slim.annotation.Layout
import io.reactivex.functions.Consumer

@Layout(R.layout.host_screen)
class HostViewAndroid : HostView(), AndroidScreen {

    private lateinit var view: View

    override fun bind(holder: ScreenHolder, x: HostScreen.Presenter, knots: MutableCollection<Knot<*, *>>) {
        //noop
    }

    override fun onCreateView(parent: ViewGroup): View {
        view = Slim.createLayout(parent.context, this, parent)
        return view
    }

    override fun updatePage(): Consumer<HostScreen.Page> {
        return Consumer {}
    }
}