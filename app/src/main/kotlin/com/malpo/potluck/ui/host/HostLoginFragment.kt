package com.malpo.potluck.ui.host

import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.ui.host.screen.HostLoginScreen
import com.malpo.potluck.ui.screen.ScreenFragment

class HostLoginFragment : ScreenFragment<HostLoginScreen.Presenter, HostLoginScreen.View, HostLoginViewAndroid>() {

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun buildView(): HostLoginViewAndroid {
        return HostLoginViewAndroid()
    }
}