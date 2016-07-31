package com.malpo.potluck.models

import com.squareup.moshi.Json

data class Token(@Json(name = "access_token") val accessToken: String,
                 @Json(name = "token_type") val token_type: String,
                 @Json(name = "expires_in") val expiresIn: Long)