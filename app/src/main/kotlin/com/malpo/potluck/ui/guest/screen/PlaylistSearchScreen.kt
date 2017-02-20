package com.malpo.potluck.ui.guest.screen

import com.malpo.potluck.ui.screen.ScreenPresenter
import com.malpo.potluck.ui.screen.ScreenView
import dagger.Module
import dagger.Provides

interface PlaylistSearchScreen {

    @Module
    class PresenterModule {
        @Provides internal fun presenter(presenter: PlaylistSearchPresenter): Presenter = presenter
    }

    interface Presenter : ScreenPresenter<View, Presenter> {}
    interface View : ScreenView<View, Presenter> {}
}