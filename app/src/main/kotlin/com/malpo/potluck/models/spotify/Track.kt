package com.malpo.potluck.models.spotify

import com.squareup.moshi.Json

data class TrackObject(@Json(name = "tracks") val items : Items)

data class Items(@Json(name = "items") val tracks : List<Track>)

data class Track(@Json(name = "album") val album : Album,
                 @Json(name = "artists") val artists : List<Artist>,
                 @Json(name = "id") val id : String,
                 @Json(name = "name") val name : String,
                 @Json(name = "uri") val uri : String)

data class Album(@Json(name = "id") val id : String,
                 @Json(name = "name") val name : String,
                 @Json(name = "uri") val uri : String)

data class Artist(@Json(name = "id") val id: String,
                  @Json(name = "name") val name : String,
                  @Json(name = "uri") val uri : String)
