package com.malpo.potluck.models.spotify

import com.squareup.moshi.Json

data class TrackResponse(@Json(name = "tracks") val trackItems: TrackItems)

data class TrackItems(@Json(name = "trackItems") val tracks: List<Track>)

data class Track(@Json(name = "album") val album: Album,
                 @Json(name = "artists") val artists: List<Artist>,
                 @Json(name = "id") val id: String,
                 @Json(name = "name") val name: String,
                 @Json(name = "uri") val uri: String)

data class Album(@Json(name = "id") val id: String,
                 @Json(name = "name") val name: String,
                 @Json(name = "uri") val uri: String)

data class Artist(@Json(name = "id") val id: String,
                  @Json(name = "name") val name: String,
                  @Json(name = "uri") val uri: String)
