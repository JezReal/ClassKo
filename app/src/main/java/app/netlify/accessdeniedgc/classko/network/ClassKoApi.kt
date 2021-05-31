package app.netlify.accessdeniedgc.classko.network

import retrofit2.Response
import retrofit2.http.*

interface ClassKoApi {

    @GET("schedule/{id}")
    suspend fun getSchedule(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Schedule>

    @GET("schedules/dummy")
    suspend fun getClassSchedules(
        @Header("Authorization") token: String
    ): Response<Schedule>

    @POST("schedule")
    suspend fun addSchedule(
        @Header("Authorization") token: String,
        @Body schedule: Schedule
    ): Response<ScheduleResponse>
}