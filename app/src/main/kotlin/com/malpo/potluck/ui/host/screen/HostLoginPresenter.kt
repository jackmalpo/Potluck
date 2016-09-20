package com.malpo.potluck.ui.host.screen

import android.app.Activity
import android.content.Intent
import com.malpo.potluck.misc.Knot
import com.malpo.potluck.misc.Knot.Companion.tie
import com.malpo.potluck.networking.spotify.host.SpotifyHostAuthenticationManager
import com.malpo.potluck.preferences.PreferenceStore
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.screen.wrap
import rx.Observable.just
import timber.log.Timber
import javax.inject.Inject

open class HostLoginPresenter @Inject constructor(val hostAuth: SpotifyHostAuthenticationManager, val prefs : PreferenceStore) : HostLoginScreen.Presenter {
    fun initAuth(activity: Activity){
        if (prefs.getSpotifyGuestToken() != null){
            Timber.d("Already logged in.")
        } else {
            hostAuth.init(activity)
        }
    }
    override fun bind(holder: ScreenHolder, x: HostLoginScreen.View, knots: MutableCollection<Knot<*>>) {
        knots.wrap(
                tie(just(prefs.getSpotifyGuestToken() != null), {})
        )
    }

    fun  handleLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {
        hostAuth.handleLoginResult(requestCode, resultCode, data)
    }
}
