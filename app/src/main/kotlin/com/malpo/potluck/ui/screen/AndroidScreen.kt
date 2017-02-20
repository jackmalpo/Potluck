package com.malpo.potluck.ui.screen

import android.view.View
import android.view.ViewGroup

interface AndroidScreen {
    fun onCreateView(parent: ViewGroup): View
    fun onViewCreated(view: View) {
    }

    fun onDestroyView() {
    }
}