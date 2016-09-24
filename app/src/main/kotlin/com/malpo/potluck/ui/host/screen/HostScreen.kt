package com.malpo.potluck.ui.host.screen

import com.malpo.potluck.ui.screen.ScreenPresenter
import com.malpo.potluck.ui.screen.ScreenView
import dagger.Module
import dagger.Provides
import rx.functions.Action1

interface HostScreen {

    companion object {
        val prefix = "host"
    }

    enum class Page constructor(val value: String) {
        login("${prefix}_login"),
        playlist_selection("${prefix}_playlist_selection")
    }

    @Module
    class PresenterModule {
        @Provides
        internal fun presenter(presenter: HostPresenter): HostScreen.Presenter = presenter
    }

    interface Presenter : ScreenPresenter<View, Presenter> {}
    interface View : ScreenView<View, Presenter> {
        fun updatePage(): Action1<Page>
    }

}