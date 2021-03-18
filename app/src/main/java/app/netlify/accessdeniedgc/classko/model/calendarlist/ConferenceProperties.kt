package app.netlify.accessdeniedgc.classko.model.calendarlist


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConferenceProperties(
    @Json(name = "allowedConferenceSolutionTypes")
    val allowedConferenceSolutionTypes: List<String>
)