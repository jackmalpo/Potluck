package com.malpo.potluck.networking.spotify

import com.malpo.potluck.models.spotify.PlaylistResponse
import com.malpo.potluck.models.spotify.Token
import com.malpo.potluck.models.spotify.TrackResponse
import retrofit2.http.*
import rx.Observable

interface SpotifyService {

    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    fun guestToken(@Header("Authorization") auth: String, @Field("grant_type") type: String): Observable<Token>

    @FormUrlEncoded
    @POST("https://accounts.spotify.com/api/token")
    fun hostToken(@Header("Authorization") auth: String, @Field("grant_type") type: String, @Field("code") code: String, @Field("redirect_uri") redirect: String): Observable<Token>

    @GET("/v1/search")
    fun searchTrack(@Header("Authorization") auth: String, @QueryMap params: Map<String, String>): Observable<TrackResponse>

    @GET("/v1/me/playlists")
    fun getPlaylists(@Header("Authorization") auth: String) : Observable<PlaylistResponse>
}