package com.malpo.potluck.ui.host.login.screen

import android.app.Activity
import android.content.Intent
import com.malpo.potluck.misc.Knot
import com.malpo.potluck.misc.Knot.Companion.tie
import com.malpo.potluck.networking.spotify.host.SpotifyHostLoginAuthManager
import com.malpo.potluck.preferences.PreferenceStore
import com.malpo.potluck.ui.host.screen.HostScreen
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.wrap
import javax.inject.Inject

class HostLoginPresenter @Inject constructor(val hostAuth: SpotifyHostLoginAuthManager, val prefs: PreferenceStore) : HostLoginScreen.Presenter {

    fun initAuth(activity: Activity) {
        prefs.spotifyHostToken().subscribe { if (it.accessToken.isEmpty()) hostAuth.init(activity) }
    }

    override fun bind(holder: ScreenHolder, x: HostLoginScreen.View, knots: MutableCollection<Knot<*>>) {
        knots.wrap(
                tie(hostAuth.tokenResult(), { holder.goTo(HostScreen.Page.playlist_selection.value) })
        )
    }

    fun handleLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {
        hostAuth.handleLoginResult(requestCode, resultCode, data)
    }
}