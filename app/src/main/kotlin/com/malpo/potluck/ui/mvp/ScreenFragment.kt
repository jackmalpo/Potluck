package com.malpo.potluck.ui.mvp

import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.extensions.bindToFragment
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

abstract class ScreenFragment<P : ScreenPresenter<V, P>, V : ScreenView<V, P>, AV : V> : BaseFragment() {

    private val KEY_PRESENTER_ID = "key:presenterId"

    @Inject
    protected lateinit var presenterProvider: Provider<P>

    protected lateinit var presenter: P

    protected var uiView = createUiView()

    private var presenterId = 0

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (uiView is AndroidScreen) {
            return (uiView as AndroidScreen).onCreateView(container!!)
        } else {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (uiView is AndroidScreen) {
            (uiView as AndroidScreen).onViewCreated(view!!)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject(viewComponent())
        if (presenterId < 1) {
            if (savedInstanceState != null) {
                presenterId = savedInstanceState.getInt(KEY_PRESENTER_ID, View.generateViewId())
            } else {
                presenterId = View.generateViewId()
            }
        }
        var p = activityState.presenterCache.get(presenterId)
        if (p == null || !getPresenterClass().isInstance(p)) {
            p = presenterProvider.get()
            activityState.presenterCache.put(presenterId, p)
        }
        presenter = p as P

        presenter = presenterProvider.get()
    }

    override fun onStart() {
        super.onStart()
        val pairs = ArrayList<Knot<*>>()
        presenter.bind(pairs, uiView)
        uiView.bind(pairs, presenter)
        for (p in pairs) {
            @Suppress("UNCHECKED_CAST")
            p.from.bindToFragment(lifeCycleSubject).observeOn(AndroidSchedulers.mainThread()).subscribe(p.to as Observer<in Any?>)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (uiView is AndroidScreen) {
            (uiView as AndroidScreen).onDestroyView()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PRESENTER_ID, presenterId)
    }

    abstract fun inject(component: ViewComponent)

    abstract fun getPresenterClass(): Class<out P>

    abstract fun createUiView(): AV
}
