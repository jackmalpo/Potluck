package com.malpo.potluck.di

class DaggerHolder {

    companion object {
        val instance = DaggerHolder()
    }

    lateinit var component: ApplicationComponent

    fun setDaggerComponent(component: ApplicationComponent) {
        this.component = component
    }

    fun component(): ApplicationComponent {
        return component
    }
}
