package com.malpo.potluck.di.module

import android.content.Context
import com.malpo.potluck.di.qualifiers.scope.EachActivity
import com.malpo.potluck.ui.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides @EachActivity fun provideContext(activity: BaseActivity): Context = activity

    @Provides @EachActivity
    fun provideActivity(): BaseActivity = activity
}