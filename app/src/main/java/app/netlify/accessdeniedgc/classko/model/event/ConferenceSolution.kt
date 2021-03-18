package app.netlify.accessdeniedgc.classko.model.event


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConferenceSolution(
    @Json(name = "iconUri")
    val iconUri: String,
    @Json(name = "key")
    val key: Key,
    @Json(name = "name")
    val name: String
)