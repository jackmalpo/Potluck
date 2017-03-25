package com.malpo.potluck.networking.spotify

import com.malpo.potluck.models.spotify.Token
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface SpotifyTokenService {

    @FormUrlEncoded
    @POST("/api/token")
    fun guestToken(@Header("Authorization") auth: String, @Field("grant_type") type: String): Observable<Token>

    @FormUrlEncoded
    @POST("/api/token")
    fun hostToken(@Header("Authorization") auth: String, @Field("grant_type") type: String, @Field("code") code: String,
                  @Field("redirect_uri") redirect: String): Observable<Token>
}