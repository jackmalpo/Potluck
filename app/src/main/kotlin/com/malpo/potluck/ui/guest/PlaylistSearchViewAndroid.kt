package com.malpo.potluck.ui.guest

import android.view.View
import android.view.ViewGroup
import com.malpo.potluck.R
import com.malpo.potluck.knot.Knot
import com.malpo.potluck.knot.ObservableKnot
import com.malpo.potluck.ui.guest.screen.PlaylistSearchScreen
import com.malpo.potluck.ui.guest.screen.PlaylistSearchView
import com.malpo.potluck.ui.screen.AndroidScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.metova.slim.Slim
import com.metova.slim.annotation.Layout

@Layout(R.layout.playlist_search_screen)
class PlaylistSearchViewAndroid : PlaylistSearchView(), AndroidScreen {

    private lateinit var view: View

    override fun bind(holder: ScreenHolder, x: PlaylistSearchScreen.Presenter, knots: MutableCollection<Knot<*, *>>) {
        //noop
    }

    override fun onCreateView(parent: ViewGroup): View {
        view = Slim.createLayout(parent.context, this, parent)
        return view
    }
}