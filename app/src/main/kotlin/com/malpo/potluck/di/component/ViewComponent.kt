package com.malpo.potluck.di.component

import com.malpo.potluck.di.scope.EachView
import com.malpo.potluck.ui.base.BaseFragment
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.welcome.WelcomeFragment
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@EachView
@Subcomponent(modules = arrayOf(ViewComponent.ScreenHolderModule::class, WelcomeScreen.PresenterModule::class))
interface ViewComponent {
    fun inject(obj: BaseFragment)
    fun inject(welcome: WelcomeFragment)

    @Module
    class ScreenHolderModule(private val screenHolder: ScreenHolder) {

        @Provides
        @EachView
        internal fun screenHolder(): ScreenHolder {
            return screenHolder
        }
    }
}
