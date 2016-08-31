package com.malpo.potluck.ui.mvp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.malpo.potluck.di.component.ActivityStateComponent
import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.ui.BaseActivity
import com.trello.rxlifecycle.FragmentEvent
import rx.subjects.BehaviorSubject
import javax.inject.Inject

open class BaseFragment : Fragment(), ScreenHost{

    protected val lifeCycleSubject = BehaviorSubject.create<FragmentEvent>()

    @Inject
    protected lateinit var activityState: ActivityStateComponent

    private lateinit var component: ViewComponent

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        lifeCycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleSubject.onNext(FragmentEvent.CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frame = FrameLayout(if (container != null) container.context else context)
        frame.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        return frame
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        component = (context as BaseActivity).component().viewComponent(ViewComponent.ScreenHostModule(this))
        component.inject(this)
    }

    override fun onStart() {
        lifeCycleSubject.onNext(FragmentEvent.START)
        super.onStart()
    }

    override fun onResume() {
        lifeCycleSubject.onNext(FragmentEvent.RESUME)
        super.onResume()
    }

    override fun onPause() {
        lifeCycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifeCycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifeCycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifeCycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifeCycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }

    protected fun viewComponent(): ViewComponent {
        return component
    }
}
