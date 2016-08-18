package com.malpo.potluck

import android.content.Context
import com.malpo.potluck.firebase.rx.Firebase
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestModule {

    @Provides
    @Singleton
    fun provideMockContext(): Context {
        return mock()
    }

    @Provides
    @Singleton
    fun providesFirebase(): Firebase {
        return mock()
    }
}