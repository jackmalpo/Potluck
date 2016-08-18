package com.malpo.potluck.di

import com.malpo.potluck.BaseComponent

class DaggerHolder {

    companion object {
        val instance = DaggerHolder()
    }

    lateinit var component: BaseComponent

    fun setDaggerComponent(component: BaseComponent) {
        this.component = component
    }
}
