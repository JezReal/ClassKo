package app.netlify.accessdeniedgc.classko.model.calendar


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Notification(
    @Json(name = "method")
    val method: String,
    @Json(name = "type")
    val type: String
)