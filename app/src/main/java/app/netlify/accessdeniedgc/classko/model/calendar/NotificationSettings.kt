package app.netlify.accessdeniedgc.classko.model.calendar


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotificationSettings(
    @Json(name = "notifications")
    val notifications: List<Notification>
)