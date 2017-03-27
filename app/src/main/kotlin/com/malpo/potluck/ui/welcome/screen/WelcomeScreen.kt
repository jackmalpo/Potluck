package com.malpo.potluck.ui.welcome.screen

import com.jakewharton.rxrelay2.PublishRelay
import com.malpo.potluck.ui.screen.ScreenPresenter
import com.malpo.potluck.ui.screen.ScreenView
import dagger.Module
import dagger.Provides

interface WelcomeScreen {

    @Module
    class PresenterModule {
        @Provides
        internal fun presenter(presenter: WelcomePresenter): Presenter = presenter
    }

    interface Presenter : ScreenPresenter<View, Presenter> {}

    interface View : ScreenView<View, Presenter> {
        val hostClicks: PublishRelay<Unit>
        val guestClicks: PublishRelay<Unit>
    }
}