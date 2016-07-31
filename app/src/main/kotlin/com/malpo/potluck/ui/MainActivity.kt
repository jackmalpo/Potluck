package com.malpo.potluck.ui

import android.content.Context
import android.os.Bundle
import com.malpo.potluck.R
import com.malpo.potluck.di.DaggerHolder
import com.malpo.potluck.firebase.rx.Firebase
import com.malpo.potluck.networking.SpotifyClient
import com.metova.flyingsaucer.defaultActivityParams
import com.metova.flyingsaucer.ui.base.BaseActivity
import com.metova.slim.annotation.Layout
import kotlinx.android.synthetic.main.activity_main.*
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@Layout(R.layout.activity_main)
class MainActivity : BaseActivity() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var firebase: Firebase

    @Inject
    lateinit var spotifyClient: SpotifyClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerHolder.instance.component.inject(this)

        test_text.text = "Yo yo yo..."

        val myRef = firebase.database.getReference("message")
        myRef.setValue("Hello, World!")

        firebase.observeValueEvent(myRef)
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        { dataSnapshot ->
                            val value = dataSnapshot.getValue(String::class.java)
                            Timber.d("Value is: $value")
                        },
                        { Timber.e(it, it.message) }
                )


        spotifyClient.getToken()
                .defaultActivityParams(mLifecycleSubject)
                .subscribe(
                        {
                            val token = it
                            Timber.d(token.toString())
                        },
                        {
                            Timber.e(it, it.message)
                        }
                )


    }
}
