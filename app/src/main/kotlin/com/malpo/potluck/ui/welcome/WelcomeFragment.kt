package com.malpo.potluck.ui.welcome

import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.ui.screen.ScreenFragment
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen

class WelcomeFragment : ScreenFragment<WelcomeScreen.Presenter, WelcomeScreen.View, WelcomeViewAndroid>() {

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun buildView(): WelcomeViewAndroid {
        return WelcomeViewAndroid()
    }
}