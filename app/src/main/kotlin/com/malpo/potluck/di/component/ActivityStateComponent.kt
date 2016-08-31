package com.malpo.potluck.di.component

import android.util.SparseArray
import com.malpo.potluck.di.module.ActivityModule
import com.malpo.potluck.di.scope.EachActivityState
import com.malpo.potluck.ui.PotluckActivity
import com.malpo.potluck.ui.mvp.ScreenPresenter
import com.malpo.potluck.ui.BaseActivity
import dagger.Subcomponent

@EachActivityState
@Subcomponent
abstract class ActivityStateComponent {

    var presenterCache: SparseArray<ScreenPresenter<*, *>> = SparseArray()

    abstract fun onCreate(activityModule: ActivityModule): ActivityComponent

    abstract fun inject(activity: PotluckActivity)

    fun onCreate(baseActivity: BaseActivity): ActivityComponent {
        return onCreate(ActivityModule(baseActivity))
    }
}