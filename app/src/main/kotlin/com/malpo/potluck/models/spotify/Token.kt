package com.malpo.potluck.models.spotify

import com.squareup.moshi.Json

data class Token(@Json(name = "access_token") val accessToken: String = "",
                 @Json(name = "token_type") val token_type: String = "",
                 @Json(name = "scope") val scope: String = "",
                 @Json(name = "expires_in") val expiresIn: Long = 0,
                 @Json(name = "refresh_token") val refreshToken: String = "")