package com.malpo.potluck.ui.guest

import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.ui.guest.screen.PlaylistSearchScreen
import com.malpo.potluck.ui.screen.ScreenFragment

class PlaylistSearchFragment : ScreenFragment<PlaylistSearchScreen.Presenter, PlaylistSearchScreen.View, PlaylistSearchViewAndroid>() {

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun buildView(): PlaylistSearchViewAndroid {
        return PlaylistSearchViewAndroid()
    }
}