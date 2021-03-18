package app.netlify.accessdeniedgc.classko.model.event


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class End(
    @Json(name = "date")
    val date: String,
    @Json(name = "dateTime")
    val dateTime: String
)