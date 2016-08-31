package com.malpo.potluck.ui

import android.os.Bundle
import com.malpo.potluck.R
import com.malpo.potluck.ui.welcome.WelcomeFragment

class PotluckActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.potluck_activity)
        supportFragmentManager.beginTransaction().replace(R.id.container, WelcomeFragment()).commit()
    }
}
