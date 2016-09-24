package com.malpo.potluck.ui.host.playlist_selection

import android.view.View
import android.view.ViewGroup
import com.malpo.potluck.R
import com.malpo.potluck.misc.Knot
import com.malpo.potluck.ui.host.playlist_selection.screen.PlaylistSelectionScreen
import com.malpo.potluck.ui.host.playlist_selection.screen.PlaylistSelectionView
import com.malpo.potluck.ui.screen.AndroidScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.metova.slim.Slim
import com.metova.slim.annotation.Layout

@Layout(R.layout.host_playlist_selection_screen)
class PlaylistSelectionViewAndroid : PlaylistSelectionView(), AndroidScreen {

    private lateinit var view: View

    override fun bind(holder: ScreenHolder, x: PlaylistSelectionScreen.Presenter, knots: MutableCollection<Knot<*>>) {
    }

    override fun onCreateView(parent: ViewGroup): View {
        view = Slim.createLayout(parent.context, this, parent)
        return view
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
    }
}