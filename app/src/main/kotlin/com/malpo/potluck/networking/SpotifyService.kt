package com.malpo.potluck.networking

import com.malpo.potluck.models.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import rx.Observable

interface SpotifyService {

    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    fun getToken(@Field("grant_type") type : String, @Header("Authorization") auth : String) : Observable<Token>

}