package com.malpo.potluck.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.malpo.potluck.PotluckApplication
import com.malpo.potluck.di.component.ActivityComponent
import com.malpo.potluck.di.component.ActivityStateComponent
import com.trello.rxlifecycle.ActivityEvent
import rx.subjects.BehaviorSubject

open class BaseActivity : AppCompatActivity() {

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    private lateinit var component: ActivityComponent

    private lateinit var stateComponent: ActivityStateComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stateComponent = lastCustomNonConfigurationInstance as ActivityStateComponent? ?: PotluckApplication.component.newActivityState()
        component = stateComponent.onCreate(this)

        lifecycleSubject.onNext(ActivityEvent.CREATE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleSubject.onNext(ActivityEvent.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
    }

    override fun onPause() {
        super.onPause()
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onRetainCustomNonConfigurationInstance(): ActivityStateComponent {
        return stateComponent
    }

    fun state(): ActivityStateComponent {
        return stateComponent
    }

    fun component(): ActivityComponent {
        return component
    }
}
