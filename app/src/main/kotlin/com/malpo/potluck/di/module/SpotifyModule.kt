package com.malpo.potluck.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.malpo.potluck.BuildConfig
import com.malpo.potluck.di.qualifiers.Guest
import com.malpo.potluck.di.qualifiers.Host
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
open class SpotifyModule {

    internal companion object {
        private val BASE_URL = "https://api.spotify.com"
    }

    @Provides
    @Named("pre_auth") //needed because we need to pass built client to below method.
    open fun provideNoAuthOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    @Provides
    @Guest
    open fun provideGuestAuthOkHttpClient(@Named("pre_auth") okHttpClient: OkHttpClient,
                                     moshi: Moshi,
                                     prefs: PreferenceStore): OkHttpClient {
        return buildClient(okHttpClient, SpotifyGuestAuthenticator(okHttpClient, moshi, prefs))
    }

    @Provides
    @Host
    open fun provideHostAuthOkHttpClient(@Named("pre_auth") okHttpClient: OkHttpClient,
                                    moshi: Moshi,
                                    prefs: PreferenceStore): OkHttpClient {
        return buildClient(okHttpClient, SpotifyHostAuthenticator(okHttpClient, moshi, prefs))
    }

    @Provides
    @Guest
    open fun provideGuestRetrofit(@Guest client: OkHttpClient, moshi: Moshi): Retrofit {
        return buildRetrofit(client, moshi)
    }

    @Provides
    @Host
    open fun provideHostRetrofit(@Host client: OkHttpClient, moshi: Moshi): Retrofit {
        return buildRetrofit(client, moshi)
    }

    @Provides
    @Guest
    open fun provideGuestSpotifyService(@Guest retrofit: Retrofit): SpotifyService {
        return buildService(retrofit)
    }

    @Provides
    @Host
    open fun provideHostSpotifyService(@Host retrofit: Retrofit): SpotifyService {
        return buildService(retrofit)
    }

    open fun buildClient(okHttpClient: OkHttpClient, authenticator: Authenticator): OkHttpClient {
        return okHttpClient.newBuilder()
                .authenticator(authenticator)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    open fun buildRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build()
    }

    open fun buildService(retrofit: Retrofit): SpotifyService {
        return retrofit.create(SpotifyService::class.java)
    }
}
