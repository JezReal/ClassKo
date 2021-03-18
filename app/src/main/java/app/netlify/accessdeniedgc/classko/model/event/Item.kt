package app.netlify.accessdeniedgc.classko.model.event


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "attendees")
    val attendees: List<Attendee>,
    @Json(name = "conferenceData")
    val conferenceData: ConferenceData,
    @Json(name = "created")
    val created: String,
    @Json(name = "creator")
    val creator: Creator,
    @Json(name = "description")
    val description: String,
    @Json(name = "end")
    val end: End,
    @Json(name = "etag")
    val etag: String,
    @Json(name = "eventType")
    val eventType: String,
    @Json(name = "hangoutLink")
    val hangoutLink: String,
    @Json(name = "htmlLink")
    val htmlLink: String,
    @Json(name = "iCalUID")
    val iCalUID: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "kind")
    val kind: String,
    @Json(name = "location")
    val location: String,
    @Json(name = "organizer")
    val organizer: Organizer,
    @Json(name = "reminders")
    val reminders: Reminders,
    @Json(name = "sequence")
    val sequence: Int,
    @Json(name = "start")
    val start: Start,
    @Json(name = "status")
    val status: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "transparency")
    val transparency: String,
    @Json(name = "updated")
    val updated: String
)