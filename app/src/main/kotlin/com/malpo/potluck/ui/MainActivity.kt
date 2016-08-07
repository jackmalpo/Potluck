package com.malpo.potluck.ui

import android.content.Context
import android.os.Bundle
import com.malpo.potluck.R
import com.malpo.potluck.di.DaggerHolder
import com.malpo.potluck.extensions.defaultActivityParams
import com.malpo.potluck.firebase.rx.Firebase
import com.malpo.potluck.networking.SpotifyGuestClient
import com.metova.flyingsaucer.ui.base.BaseActivity
import com.metova.slim.annotation.Layout
import timber.log.Timber
import javax.inject.Inject

@Layout(R.layout.activity_main)
class MainActivity : BaseActivity() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var firebase: Firebase

    @Inject
    lateinit var spotifyClient: SpotifyGuestClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerHolder.instance.component.inject(this)

        spotifyClient
                .getAnonToken()
                .defaultActivityParams(mLifecycleSubject)
                .subscribe {
                    search()
                    Timber.d("Retrieved access token ${it.accessToken}")
                }
    }

    fun search() {
        spotifyClient
                .search("the beatles")
                .defaultActivityParams(mLifecycleSubject)
                .subscribe({

                }, {

                })
    }
}
