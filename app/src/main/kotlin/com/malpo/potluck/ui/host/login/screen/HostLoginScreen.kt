package com.malpo.potluck.ui.host.login.screen

import com.malpo.potluck.ui.screen.ScreenPresenter
import com.malpo.potluck.ui.screen.ScreenView
import dagger.Module
import dagger.Provides
import io.reactivex.functions.Consumer

interface HostLoginScreen {

    @Module
    class PresenterModule {
        @Provides
        internal fun presenter(presenter: HostLoginPresenter): HostLoginScreen.Presenter = presenter
    }

    interface Presenter : ScreenPresenter<View, Presenter> {}
    interface View : ScreenView<View, Presenter> {
        fun showHeader(): Consumer<Boolean>
    }

}