package com.malpo.potluck.di.component

import android.util.SparseArray
import com.malpo.potluck.di.module.ActivityModule
import com.malpo.potluck.di.scope.EachActivityState
import com.malpo.potluck.ui.BaseActivity
import com.malpo.potluck.ui.PotluckActivity
import com.malpo.potluck.ui.screen.ScreenPresenter
import dagger.Subcomponent

@EachActivityState
@Subcomponent
abstract class ActivityStateComponent {

    private var _presenters: SparseArray<ScreenPresenter<*, *>> = SparseArray()
    var presenters: SparseArray<ScreenPresenter<*, *>>
        get() = _presenters
        set(value) {
            _presenters = value
        }

    abstract fun onCreate(activityModule: ActivityModule): ActivityComponent

    abstract fun inject(activity: PotluckActivity)

    fun onCreate(baseActivity: BaseActivity): ActivityComponent {
        return onCreate(ActivityModule(baseActivity))
    }
}