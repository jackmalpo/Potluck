package com.malpo.potluck.di.component

import com.malpo.potluck.di.scope.EachView
import com.malpo.potluck.ui.base.BaseFragment
import com.malpo.potluck.ui.guest.PlaylistSearchFragment
import com.malpo.potluck.ui.guest.screen.PlaylistSearchScreen
import com.malpo.potluck.ui.host.HostLoginFragment
import com.malpo.potluck.ui.host.screen.HostLoginScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.welcome.WelcomeFragment
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@EachView
@Subcomponent(modules = arrayOf(
        ViewComponent.ScreenHolderModule::class,
        WelcomeScreen.PresenterModule::class,
        HostLoginScreen.PresenterModule::class,
        PlaylistSearchScreen.PresenterModule::class
))
interface ViewComponent {
    fun screenHolder(): ScreenHolder
    fun inject(obj: BaseFragment)
    fun inject(welcome: WelcomeFragment)
    fun inject(hostLogin: HostLoginFragment)
    fun inject(playlistSearch: PlaylistSearchFragment)

    @Module
    class ScreenHolderModule(private val screenHolder: ScreenHolder) {

        @Provides
        @EachView
        internal fun screenHolder(): ScreenHolder {
            return screenHolder
        }
    }
}
