package com.malpo.potluck.ui.welcome.screen

import com.malpo.potluck.ui.mvp.ScreenPresenter
import com.malpo.potluck.ui.mvp.ScreenView
import dagger.Module
import dagger.Provides
import rx.Observable

interface WelcomeScreen {

    @Module
    class PresenterModule {
        @Provides
        internal fun presenter(presenter: WelcomePresenter): Presenter {
            return presenter
        }
    }

    interface Presenter : ScreenPresenter<View, Presenter> {
    }

    interface View : ScreenView<View, Presenter> {
        fun hostClicked(): Observable<Unit>
        fun guestClicked(): Observable<Unit>
    }
}