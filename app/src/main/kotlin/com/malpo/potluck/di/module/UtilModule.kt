package com.malpo.potluck.di.module

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Provides @Singleton fun provideMoshi(): Moshi = Moshi.Builder().build()

}