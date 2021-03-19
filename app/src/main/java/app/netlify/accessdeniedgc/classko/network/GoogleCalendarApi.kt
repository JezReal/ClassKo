package app.netlify.accessdeniedgc.classko.network

import app.netlify.accessdeniedgc.classko.model.calendar.CalendarList
import app.netlify.accessdeniedgc.classko.model.event.EventsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GoogleCalendarApi {


    @GET("users/me/calendarList")
    suspend fun getCalendarList(): Response<CalendarList>

    @GET("calendars/{calendarId}/events")
    suspend fun getEventList(@Path("calendarId") id: String): Response<EventsList>
}