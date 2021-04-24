package app.netlify.accessdeniedgc.classko.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Schedule(
    @Json(name = "days")
    val days: List<String>,
    @Json(name = "endTime")
    val endTime: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "startTime")
    val startTime: String,
    @Json(name = "subject")
    val subject: String
)