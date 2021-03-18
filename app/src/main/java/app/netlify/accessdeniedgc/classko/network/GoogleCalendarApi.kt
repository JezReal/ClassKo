package app.netlify.accessdeniedgc.classko.network

import app.netlify.accessdeniedgc.classko.model.calendar.CalendarList
import app.netlify.accessdeniedgc.classko.model.event.EventsList
import retrofit2.Response
import retrofit2.http.GET

interface GoogleCalendarApi {


    @GET
    fun getCalendarList(): Response<List<CalendarList>>

    @GET
    fun getEventList(): Response<List<EventsList>>
}