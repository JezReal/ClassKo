package app.netlify.accessdeniedgc.classko.model.event


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntryPoint(
    @Json(name = "entryPointType")
    val entryPointType: String,
    @Json(name = "label")
    val label: String,
    @Json(name = "pin")
    val pin: String,
    @Json(name = "regionCode")
    val regionCode: String,
    @Json(name = "uri")
    val uri: String
)