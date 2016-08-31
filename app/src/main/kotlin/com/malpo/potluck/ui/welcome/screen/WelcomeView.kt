package com.malpo.potluck.ui.welcome.screen

import com.jakewharton.rxrelay.PublishRelay
import com.malpo.potluck.ui.mvp.Knot
import rx.Observable

abstract class WelcomeView : WelcomeScreen.View {

    val hostClicks = PublishRelay.create<Unit>()
    val guestClicks = PublishRelay.create<Unit>()

    override fun hostClicked(): Observable<Unit> {
        return hostClicks.asObservable()
    }

    override fun guestClicked(): Observable<Unit> {
        return guestClicks.asObservable()
    }

    override fun bind(elements: MutableCollection<Knot<*>>, x: WelcomeScreen.Presenter) {
        //noop
    }
}