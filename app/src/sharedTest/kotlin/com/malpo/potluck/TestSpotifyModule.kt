package com.malpo.potluck

import com.malpo.potluck.di.module.SpotifyModule
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class TestSpotifyModule : SpotifyModule() {

    val mockWebServer = MockWebServer()

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = mockWebServer

    override fun buildRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build()
    }
}