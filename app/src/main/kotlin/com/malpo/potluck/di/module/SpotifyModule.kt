package com.malpo.potluck.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.malpo.potluck.BuildConfig
import com.malpo.potluck.di.qualifiers.*
import com.malpo.potluck.networking.spotify.SpotifyService
import com.malpo.potluck.networking.spotify.SpotifyTokenService
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
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named


@Module(includes = arrayOf(AndroidModule::class,
        MockWebServerModule::class,
        PreferencesModule::class,
        UtilModule::class))
open class SpotifyModule {

    companion object {
        private val STANDARD_BASE_URL = "https://api.spotify.com"
        private val TOKEN_BASE_URL = "https://accounts.spotify.com"
    }

    @Provides @Named("pre_auth") fun provideNoAuthOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    /* HOST */
    @Provides @Host open fun provideHostBaseUrl(@MockWeb mockWebUrl: String, @IsTest isTest: Boolean): String {
        return if (isTest) mockWebUrl else STANDARD_BASE_URL
    }

    @Provides @Host fun provideHostAuthOkHttpClient(@Named("pre_auth") okHttpClient: OkHttpClient,
                                                    @SpotifyToken tokenBaseUrl: String, moshi: Moshi,
                                                    prefs: PreferenceStore): OkHttpClient {
        return buildAuthClient(okHttpClient, SpotifyHostAuthenticator(okHttpClient, tokenBaseUrl, moshi, prefs))
    }

    @Provides @Host fun provideHostRetrofit(@Host client: OkHttpClient, moshi: Moshi, @Host baseUrl: String): Retrofit {
        return buildRetrofit(client, moshi, baseUrl)
    }

    @Provides @Host fun provideHostSpotifyService(@Host retrofit: Retrofit): SpotifyService {
        return retrofit.create(SpotifyService::class.java)
    }

    /* GUEST */
    @Provides @Guest open fun provideGuestBaseUrl(@MockWeb mockWebUrl: String, @IsTest isTest: Boolean): String {
        return if (isTest) mockWebUrl else STANDARD_BASE_URL
    }

    @Provides @Guest fun provideGuestAuthOkHttpClient(@Named("pre_auth") okHttpClient: OkHttpClient,
                                                      @SpotifyToken tokenBaseUrl: String, moshi: Moshi,
                                                      prefs: PreferenceStore): OkHttpClient {
        return buildAuthClient(okHttpClient, SpotifyGuestAuthenticator(okHttpClient, tokenBaseUrl, moshi, prefs))
    }

    @Provides @Guest fun provideGuestRetrofit(@Guest client: OkHttpClient, moshi: Moshi,
                                              @Guest baseUrl: String): Retrofit {
        return buildRetrofit(client, moshi, baseUrl)
    }

    @Provides @Guest fun provideGuestSpotifyService(@Guest retrofit: Retrofit): SpotifyService {
        return retrofit.create(SpotifyService::class.java)
    }

    /* TOKEN */
    @Provides @SpotifyToken open fun provideTokenBaseUrl(@MockWeb mockWebUrl: String, @IsTest isTest: Boolean): String {
        return if (isTest) mockWebUrl else TOKEN_BASE_URL
    }

    @Provides @SpotifyToken fun provideTokenAuth(@Named("pre_auth") okHttpClient: OkHttpClient): OkHttpClient {
        return buildClient(okHttpClient)
    }

    @Provides @SpotifyToken fun provideTokenRetrofit(@SpotifyToken client: OkHttpClient, moshi: Moshi,
                                                     @SpotifyToken baseUrl: String): Retrofit {
        return buildRetrofit(client, moshi, baseUrl)
    }

    @Provides @SpotifyToken fun provideTokenSpotifyService(@SpotifyToken retrofit: Retrofit): SpotifyTokenService {
        return retrofit.create(SpotifyTokenService::class.java)
    }

    /* BUILD */
    open fun buildAuthClient(okHttpClient: OkHttpClient, authenticator: Authenticator): OkHttpClient {
        return buildClient(okHttpClient.newBuilder().authenticator(authenticator))
    }

    open fun buildClient(okHttpClient: OkHttpClient): OkHttpClient {
        return buildClient(okHttpClient.newBuilder())
    }

    open fun buildClient(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.addNetworkInterceptor(StethoInterceptor()).build()
    }

    open fun buildRetrofit(client: OkHttpClient, moshi: Moshi, baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }
}
