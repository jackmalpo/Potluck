package com.malpo.potluck.ui.screen

import android.os.Bundle
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.View.generateViewId
import android.view.ViewGroup
import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.extensions.bindToFragment
import com.malpo.potluck.misc.Knot
import com.malpo.potluck.ui.base.BaseFragment
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

abstract class ScreenFragment<P : ScreenPresenter<V, P>, V : ScreenView<V, P>, VA : V> : BaseFragment() {

    @Inject
    protected lateinit var presenterProvider: Provider<P>

    protected lateinit var presenter: P

    protected var view = buildView()

    private var presenterId = 0

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        when (view) {
            is AndroidScreen -> return (view as AndroidScreen).onCreateView(container!!)
            else -> return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (view) {
            is AndroidScreen -> view.onViewCreated(view)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject(viewComponent())
        when (presenterId) {
            0 -> when {
                savedInstanceState != null -> presenterId = savedInstanceState.getInt(PRESENTER_KEY, generateViewId())
                else -> presenterId = generateViewId()
            }
        }
        presenter = state.presenters.get(presenterId) as P? ?: presenterProvider.get()
        state.presenters.put(presenterId, presenter)
    }


    override fun onStart() {
        super.onStart()
        val screenHolder = viewComponent().screenHolder()
        val knots = ArrayList<Knot<*>>()
        presenter.bind(screenHolder, view, knots)
        view.bind(screenHolder, presenter, knots)
        knots.forEach { knot ->
            @Suppress("UNCHECKED_CAST")
            knot.from.bindToFragment(lifeCycleSubject).observeOn(AndroidSchedulers.mainThread()).subscribe(knot.to as Observer<in Any?>)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (view is AndroidScreen) {
            (view as AndroidScreen).onDestroyView()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PRESENTER_KEY, presenterId)
    }

    abstract fun inject(component: ViewComponent)

    abstract fun buildView(): VA

    companion object {
        val PRESENTER_KEY: String = "presenter_key"
    }
}
