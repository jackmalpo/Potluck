package com.malpo.potluck

import android.app.Application
import com.firebase.client.Firebase

class PotluckApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.setAndroidContext(this)
    }
}
