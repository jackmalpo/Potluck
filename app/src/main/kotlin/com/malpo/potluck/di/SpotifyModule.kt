package com.malpo.potluck.di

import com.malpo.potluck.BuildConfig
import com.malpo.potluck.networking.SpotifyGuestAuthenticator
import com.malpo.potluck.networking.SpotifyGuestClient
import com.malpo.potluck.networking.SpotifyGuestService
import com.metova.flyingsaucer.util.PreferenceStore
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = arrayOf(PreferencesModule::class))
class SpotifyModule {

    internal companion object {
        private val BASE_URL = "https://api.spotify.com"
    }

    @Provides @Singleton fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides @Named("pre_auth") fun provideNoAuthOkHttpClient(moshi: Moshi, prefs: PreferenceStore): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    @Provides fun provideAuthOkHttpClient(@Named("pre_auth") okHttpClient: OkHttpClient, moshi: Moshi, prefs: PreferenceStore) : OkHttpClient {
        //Need reference to built client so we have to build before adding authenticator
        return okHttpClient.newBuilder()
                .authenticator(SpotifyGuestAuthenticator(okHttpClient, moshi, prefs))
                .build()
    }

    @Provides fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build()
    }

    @Provides fun provideSpotifyGuestService(retrofit: Retrofit): SpotifyGuestService {
        return retrofit.create(SpotifyGuestService::class.java)
    }

    @Provides @Singleton fun provideSpotifyGuestClient(service: SpotifyGuestService, prefs: PreferenceStore): SpotifyGuestClient {
        return SpotifyGuestClient(service, prefs)
    }
}
