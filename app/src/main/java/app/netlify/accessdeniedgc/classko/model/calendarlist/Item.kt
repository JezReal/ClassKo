package app.netlify.accessdeniedgc.classko.model.calendarlist


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "accessRole")
    val accessRole: String,
    @Json(name = "backgroundColor")
    val backgroundColor: String,
    @Json(name = "colorId")
    val colorId: String,
    @Json(name = "conferenceProperties")
    val conferenceProperties: ConferenceProperties,
    @Json(name = "defaultReminders")
    val defaultReminders: List<DefaultReminder>,
    @Json(name = "description")
    val description: String,
    @Json(name = "etag")
    val etag: String,
    @Json(name = "foregroundColor")
    val foregroundColor: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "notificationSettings")
    val notificationSettings: NotificationSettings,
    @Json(name = "primary")
    val primary: Boolean,
    @Json(name = "selected")
    val selected: Boolean,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "summaryOverride")
    val summaryOverride: String,
    @Json(name = "timeZone")
    val timeZone: String
)