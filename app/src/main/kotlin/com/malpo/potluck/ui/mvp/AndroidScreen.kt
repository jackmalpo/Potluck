package com.malpo.potluck.ui.mvp

import android.view.View
import android.view.ViewGroup

interface AndroidScreen {
    fun onCreateView(parent: ViewGroup): View
    fun onViewCreated(view: View)
    fun onDestroyView()
}