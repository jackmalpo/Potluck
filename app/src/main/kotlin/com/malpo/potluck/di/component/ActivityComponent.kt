package com.malpo.potluck.di.component

import com.malpo.potluck.di.module.ActivityModule
import com.malpo.potluck.di.scope.EachActivity
import com.malpo.potluck.ui.PotluckActivity
import dagger.Subcomponent

@EachActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun viewComponent(holderModule: ViewComponent.ScreenHolderModule): ViewComponent
    fun inject(obj: PotluckActivity)
}