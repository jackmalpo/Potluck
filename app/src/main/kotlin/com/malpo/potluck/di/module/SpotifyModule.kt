package com.malpo.potluck.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.malpo.potluck.BuildConfig
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.guest.SpotifyGuestAuthenticator
import com.malpo.potluck.networking.spotify.host.SpotifyHostAuthenticator
import com.malpo.potluck.preferences.PreferenceStore
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named


@Module(includes = arrayOf(PreferencesModule::class, UtilModule::class))
class SpotifyModule {

    internal companion object {
        private val BASE_URL = "https://api.spotify.com"
    }

    @Provides
    @Named("pre_auth") //needed because we need to pass built client to below method.
    fun provideNoAuthOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    @Provides
    @Named("guest")
    fun provideGuestAuthOkHttpClient(@Named("pre_auth") okHttpClient: OkHttpClient,
                                     moshi: Moshi,
                                     prefs: PreferenceStore): OkHttpClient {
        return buildClient(okHttpClient, SpotifyGuestAuthenticator(okHttpClient, moshi, prefs))
    }

    @Provides
    @Named("host")
    fun provideHostAuthOkHttpClient(@Named("pre_auth") okHttpClient: OkHttpClient,
                                    moshi: Moshi,
                                    prefs: PreferenceStore): OkHttpClient {
        return buildClient(okHttpClient, SpotifyHostAuthenticator(okHttpClient, moshi, prefs))
    }

    @Provides
    @Named("guest")
    fun provideGuestRetrofit(@Named("guest") client: OkHttpClient, moshi: Moshi): Retrofit {
        return buildRetrofit(client, moshi)
    }

    @Provides
    @Named("host")
    fun provideHostRetrofit(@Named("host") client: OkHttpClient, moshi: Moshi): Retrofit {
        return buildRetrofit(client, moshi)
    }

    @Provides
    @Named("guest")
    fun provideGuestSpotifyService(@Named("guest") retrofit: Retrofit): SpotifyService {
        return buildService(retrofit)
    }

    @Provides
    @Named("host")
    fun provideHostSpotifyService(@Named("host") retrofit: Retrofit): SpotifyService {
        return buildService(retrofit)
    }

    private fun buildClient(okHttpClient: OkHttpClient, authenticator: Authenticator): OkHttpClient {
        return okHttpClient.newBuilder()
                .authenticator(authenticator)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    private fun buildRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build()
    }

    private fun buildService(retrofit: Retrofit): SpotifyService {
        return retrofit.create(SpotifyService::class.java)
    }
}
