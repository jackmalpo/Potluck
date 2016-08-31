package com.malpo.potluck.di.module

import android.content.Context
import com.malpo.potluck.di.scope.EachActivity
import com.malpo.potluck.ui.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @EachActivity
    fun providesContext(activity: BaseActivity): Context {
        return activity
    }

    @Provides
    @EachActivity
    fun providesActivity(): BaseActivity {
        return activity
    }
}