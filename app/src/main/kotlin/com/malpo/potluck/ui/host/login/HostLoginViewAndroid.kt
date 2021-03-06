package com.malpo.potluck.ui.host.login

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.malpo.potluck.R
import com.malpo.potluck.knot.Knot
import com.malpo.potluck.ui.host.login.screen.HostLoginScreen
import com.malpo.potluck.ui.host.login.screen.HostLoginView
import com.malpo.potluck.ui.screen.AndroidScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.metova.slim.Slim
import com.metova.slim.annotation.Layout
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.host_login_screen.view.*

@Layout(R.layout.host_login_screen)
class HostLoginViewAndroid : HostLoginView(), AndroidScreen {

    private lateinit var view: View

    override fun bind(holder: ScreenHolder, x: HostLoginScreen.Presenter, knots: MutableCollection<Knot<*, *>>) {
        //noop
    }

    override fun onCreateView(parent: ViewGroup): View {
        view = Slim.createLayout(parent.context, this, parent)
        return view
    }

    override fun showHeader(): Consumer<Boolean> = Consumer {
        show ->
        view.host_header.visibility = if (show) VISIBLE else GONE
    }
}