package app.netlify.accessdeniedgc.classko.model.event


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConferenceData(
    @Json(name = "conferenceId")
    val conferenceId: String,
    @Json(name = "conferenceSolution")
    val conferenceSolution: ConferenceSolution,
    @Json(name = "entryPoints")
    val entryPoints: List<EntryPoint>,
    @Json(name = "signature")
    val signature: String
)