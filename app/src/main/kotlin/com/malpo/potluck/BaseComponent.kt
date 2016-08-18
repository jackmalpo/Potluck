package com.malpo.potluck

import com.malpo.potluck.ui.MainActivity

interface BaseComponent {
    fun inject(mainActivity: MainActivity)
}