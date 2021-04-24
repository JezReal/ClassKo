package app.netlify.accessdeniedgc.classko.network

import app.netlify.accessdeniedgc.classko.model.Schedule
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleApi {

    @GET("schedules")
    suspend fun getScheduleList(): Response<List<Schedule>>
}