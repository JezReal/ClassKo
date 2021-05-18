package app.netlify.accessdeniedgc.classko.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Schedule(
    @Json(name = "scheduleItems")
    val scheduleItems: List<ScheduleItem>
)