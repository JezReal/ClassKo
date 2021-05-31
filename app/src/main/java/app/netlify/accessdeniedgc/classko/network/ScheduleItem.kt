package app.netlify.accessdeniedgc.classko.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScheduleItem(
    @Json(name = "subjectName")
    val subjectName: String,
    @Json(name = "type")
    val type: String?,
    @Json(name = "timeHour")
    val timeHour: Int,
    @Json(name = "timeMinute")
    val timeMinute: Int,

    @Json(name = "monday")
    val monday: Boolean,
    @Json(name = "tuesday")
    val tuesday: Boolean,
    @Json(name = "wednesday")
    val wednesday: Boolean,
    @Json(name = "thursday")
    val thursday: Boolean,
    @Json(name = "friday")
    val friday: Boolean,
    @Json(name = "saturday")
    val saturday: Boolean,
    @Json(name = "sunday")
    val sunday: Boolean,
)