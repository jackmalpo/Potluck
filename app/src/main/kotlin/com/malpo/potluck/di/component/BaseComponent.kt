package com.malpo.potluck.di.component

import com.malpo.potluck.ui.PotluckActivity

interface BaseComponent {
    fun inject(potluckActivity: PotluckActivity)
}