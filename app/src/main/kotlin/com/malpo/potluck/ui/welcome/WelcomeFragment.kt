package com.malpo.potluck.ui.welcome

import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.ui.mvp.ScreenFragment
import com.malpo.potluck.ui.welcome.screen.WelcomePresenter
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen

class WelcomeFragment : ScreenFragment<WelcomeScreen.Presenter, WelcomeScreen.View, WelcomeViewAndroid>() {

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun getPresenterClass(): Class<out WelcomeScreen.Presenter> {
        return WelcomePresenter::class.java
    }

    override fun createUiView(): WelcomeViewAndroid {
        return WelcomeViewAndroid()
    }
}