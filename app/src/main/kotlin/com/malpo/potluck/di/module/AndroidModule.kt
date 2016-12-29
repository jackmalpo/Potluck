package com.malpo.potluck.di.module

import android.content.Context
import com.malpo.potluck.di.qualifiers.IsTest
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(private val appContext: Context, private val isTest: Boolean = false) {

    @Provides @Singleton fun provideApplicationContext(): Context = appContext

    @Provides @IsTest fun provideIsTest(): Boolean = isTest
}