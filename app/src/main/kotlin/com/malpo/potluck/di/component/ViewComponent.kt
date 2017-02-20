package com.malpo.potluck.di.component

import com.malpo.potluck.di.qualifiers.scope.EachView
import com.malpo.potluck.ui.base.BaseFragment
import com.malpo.potluck.ui.guest.PlaylistSearchFragment
import com.malpo.potluck.ui.guest.screen.PlaylistSearchScreen
import com.malpo.potluck.ui.host.HostFragment
import com.malpo.potluck.ui.host.login.HostLoginFragment
import com.malpo.potluck.ui.host.login.screen.HostLoginScreen
import com.malpo.potluck.ui.host.playlist_selection.PlaylistSelectionFragment
import com.malpo.potluck.ui.host.playlist_selection.screen.PlaylistSelectionScreen
import com.malpo.potluck.ui.host.screen.HostScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.welcome.WelcomeFragment
import com.malpo.potluck.ui.welcome.screen.WelcomeScreen
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@EachView
@Subcomponent(modules = arrayOf(
        HostScreen.PresenterModule::class,
        HostLoginScreen.PresenterModule::class,
        PlaylistSearchScreen.PresenterModule::class,
        PlaylistSelectionScreen.PresenterModule::class,
        ViewComponent.ScreenHolderModule::class,
        WelcomeScreen.PresenterModule::class
))
interface ViewComponent {
    fun screenHolder(): ScreenHolder
    fun inject(obj: BaseFragment)
    fun inject(host: HostFragment)
    fun inject(hostLogin: HostLoginFragment)
    fun inject(playlistSearch: PlaylistSearchFragment)
    fun inject(playlistSelection: PlaylistSelectionFragment)
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
