package com.malpo.potluck.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.malpo.potluck.R
import com.malpo.potluck.di.DaggerHolder
import com.malpo.potluck.firebase.rx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var firebase: Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerHolder.instance.component.inject(this)

        setContentView(R.layout.activity_main)
        test_text.text = "Yo yo yo..."

        val myRef = firebase.getDatabase().getReference("message")
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

    }
}
