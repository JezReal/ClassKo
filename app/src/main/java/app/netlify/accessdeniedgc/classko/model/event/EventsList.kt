package app.netlify.accessdeniedgc.classko.model.event


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventsList(
    @Json(name = "accessRole")
    val accessRole: String,
    @Json(name = "defaultReminders")
    val defaultReminders: List<DefaultReminder>,
    @Json(name = "etag")
    val etag: String,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "nextSyncToken")
    val nextSyncToken: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "timeZone")
    val timeZone: String,
    @Json(name = "updated")
    val updated: String
)