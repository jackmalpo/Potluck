package com.malpo.potluck.ui.host.screen

import com.malpo.potluck.ui.screen.ScreenPresenter
import com.malpo.potluck.ui.screen.ScreenView
import dagger.Module
import dagger.Provides

interface HostLoginScreen {

    @Module
    class PresenterModule {
        @Provides
        internal fun presenter(presenter: HostLoginPresenter): Presenter {
            return presenter
        }
    }

    interface Presenter : ScreenPresenter<View, Presenter> {}
    interface View : ScreenView<View, Presenter> {}
}