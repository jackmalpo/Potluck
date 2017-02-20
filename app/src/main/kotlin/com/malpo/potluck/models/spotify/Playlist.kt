package com.malpo.potluck.models.spotify

import com.squareup.moshi.Json

data class PlaylistResponse(@Json(name = "items") val items: List<Playlist>)

data class Playlist(@Json(name = "id") val id: String,
                         @Json(name = "images") val images: List<Image>,
                         @Json(name = "name") val name: String,
                         @Json(name = "uri") val uri: String)

data class Image(@Json(name = "url") val url : String)
