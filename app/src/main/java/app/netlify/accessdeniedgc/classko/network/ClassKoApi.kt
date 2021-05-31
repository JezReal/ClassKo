package app.netlify.accessdeniedgc.classko.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClassKoApi {

    @GET("schedule/{id}")
    suspend fun getSchedule(@Path("id") id: String): Response<Schedule>

    @GET("schedules/dummy")
    suspend fun getClassSchedules(): Response<Schedule>

    @POST("schedule")
    suspend fun addSchedule(@Body schedule: Schedule): Response<ScheduleResponse>
}