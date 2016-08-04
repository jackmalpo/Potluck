package com.malpo.potluck.di

import com.malpo.potluck.BuildConfig
import com.malpo.potluck.networking.SpotifyClient
import com.malpo.potluck.networking.SpotifyService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
class SpotifyModule {

    companion object {
        private val BASE_URL = "https://api.spotify.com"
    }

    @Provides
    fun provideSpotifyService(): SpotifyService {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
        }
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build()
        return retrofit.create(SpotifyService::class.java)
    }

    @Provides
    @Singleton
    fun provideSpotifyClient(service: SpotifyService): SpotifyClient {
        return SpotifyClient(service)
    }
}
