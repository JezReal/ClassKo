package app.netlify.accessdeniedgc.classko.model.calendarlist


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DefaultReminder(
    @Json(name = "method")
    val method: String,
    @Json(name = "minutes")
    val minutes: Int
)