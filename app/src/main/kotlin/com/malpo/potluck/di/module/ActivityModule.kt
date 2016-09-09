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
    fun provideContext(activity: BaseActivity): Context {
        return activity
    }

    @Provides
    @EachActivity
    fun provideActivity(): BaseActivity {
        return activity
    }
}