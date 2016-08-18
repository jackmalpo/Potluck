package com.malpo.potluck

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestModule::class))
interface TestComponent : BaseComponent {
    fun inject(baseUnitTest: BaseUnitTest)
}