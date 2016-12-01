package com.malpo.potluck.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(private val context: Context, private val isTest: Boolean = false) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return context
    }
}
