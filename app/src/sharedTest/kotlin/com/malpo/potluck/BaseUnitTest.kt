package com.malpo.potluck

import com.malpo.potluck.di.DaggerHolder
import org.junit.Before

open class BaseUnitTest {

    lateinit var component: TestComponent

    @Before
    @Throws(Exception::class)
    fun initComponents() {
        component = createComponent()
        DaggerHolder.instance.setDaggerComponent(component)
    }

    fun createComponent(): TestComponent {
        return DaggerTestComponent.builder()
                .testModule(TestModule())
                .build()
    }
}