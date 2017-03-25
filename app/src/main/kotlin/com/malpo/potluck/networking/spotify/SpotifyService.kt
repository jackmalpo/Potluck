package com.malpo.potluck.networking.spotify

import com.malpo.potluck.models.spotify.PlaylistResponse
import com.malpo.potluck.models.spotify.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap
import io.reactivex.Observable

interface SpotifyService {
    @GET("/v1/search")
    fun searchTrack(@Header("Authorization") auth: String, @QueryMap params: Map<String, String>): Observable<TrackResponse>

    @GET("/v1/me/playlists")
    fun getPlaylists(@Header("Authorization") auth: String): Observable<PlaylistResponse>
}