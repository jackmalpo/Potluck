package com.malpo.potluck.di.component

import com.malpo.potluck.di.scope.EachView
import com.malpo.potluck.ui.mvp.BaseFragment
import com.malpo.potluck.ui.mvp.ScreenHost
import com.malpo.potluck.ui.welcome.WelcomeFragment
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@EachView
@Subcomponent(modules = arrayOf(ViewComponent.ScreenHostModule::class, WelcomeScreen.PresenterModule::class))
interface ViewComponent {
    fun inject(obj: BaseFragment)
    fun inject(welcome: WelcomeFragment)

    @Module
    class ScreenHostModule(private val screenHost: ScreenHost) {

        @Provides
        @EachView
        internal fun screenHost(): ScreenHost {
            return screenHost
        }
    }
}
