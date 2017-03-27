package com.malpo.potluck.ui.welcome.screen

import com.jakewharton.rxrelay2.PublishRelay


abstract class WelcomeView : WelcomeScreen.View {
    override val hostClicks = PublishRelay.create<Unit>()
    override val guestClicks = PublishRelay.create<Unit>()
}