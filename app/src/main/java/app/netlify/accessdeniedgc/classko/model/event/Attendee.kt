package app.netlify.accessdeniedgc.classko.model.event


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attendee(
    @Json(name = "email")
    val email: String,
    @Json(name = "organizer")
    val organizer: Boolean,
    @Json(name = "responseStatus")
    val responseStatus: String,
    @Json(name = "self")
    val self: Boolean
)