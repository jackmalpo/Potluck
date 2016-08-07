package com.malpo.potluck.networking.spotify.guest

import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.models.spotify.TrackObject
import retrofit2.http.*
import rx.Observable

interface SpotifyGuestService {

    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    fun getAnonToken(@Header("Authorization") auth : String, @Field("grant_type") type : String) : Observable<Token>


    @GET("/v1/search")
    fun searchTrack(@Header("Authorization") auth: String, @QueryMap params: Map<String, String>): Observable<TrackObject>

}