package com.malpo.potluck.ui

import android.content.Context
import android.os.Bundle
import com.malpo.potluck.R
import com.malpo.potluck.di.DaggerHolder
import com.malpo.potluck.firebase.rx.Firebase
import com.metova.flyingsaucer.ui.base.BaseActivity
import com.metova.slim.annotation.Layout
import javax.inject.Inject

@Layout(R.layout.activity_main)
class MainActivity : BaseActivity() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var firebase: Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerHolder.instance.component.inject(this)
    }
}
