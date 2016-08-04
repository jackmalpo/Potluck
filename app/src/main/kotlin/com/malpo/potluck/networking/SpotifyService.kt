package com.malpo.potluck.networking

import com.malpo.potluck.models.spotify.Token
import retrofit2.http.*
import rx.Observable

interface SpotifyService {

    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    fun getAnonToken(@Field("grant_type") type : String, @Header("Authorization") auth : String) : Observable<Token>

    @GET("https://accounts.spotify.com/authorize")
    fun authorize(@QueryMap params : Map<String, String>) : Observable<Token>

}