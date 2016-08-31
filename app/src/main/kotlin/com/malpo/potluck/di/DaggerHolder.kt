package com.malpo.potluck.di

import com.malpo.potluck.di.component.ApplicationComponent

class DaggerHolder {

    companion object {
        val instance = DaggerHolder()
    }

    lateinit var component: ApplicationComponent

    fun setDaggerComponent(component: ApplicationComponent) {
        this.component = component
    }
}
