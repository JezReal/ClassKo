package app.netlify.accessdeniedgc.classko.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name="username")
    val username: String,
    @Json(name="password")
    val password: String
)
