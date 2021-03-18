package app.netlify.accessdeniedgc.classko.model.event


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Creator(
    @Json(name = "displayName")
    val displayName: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "self")
    val self: Boolean
)