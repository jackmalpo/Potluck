package com.malpo.potluck.di.module

import com.malpo.potluck.firebase.rx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebase(): Firebase {
        return Firebase()
    }
}