package com.malpo.potluck.ui.host.playlist_selection

import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.ui.host.playlist_selection.screen.PlaylistSelectionScreen
import com.malpo.potluck.ui.screen.ScreenFragment

class PlaylistSelectionFragment : ScreenFragment<PlaylistSelectionScreen.Presenter, PlaylistSelectionScreen.View, PlaylistSelectionViewAndroid>() {

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun buildView(): PlaylistSelectionViewAndroid {
        return PlaylistSelectionViewAndroid()
    }
}
