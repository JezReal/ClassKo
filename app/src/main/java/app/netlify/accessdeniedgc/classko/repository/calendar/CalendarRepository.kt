package app.netlify.accessdeniedgc.classko.repository.calendar

import app.netlify.accessdeniedgc.classko.model.calendar.CalendarList
import app.netlify.accessdeniedgc.classko.network.GoogleCalendarApi
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import timber.log.Timber
import javax.inject.Inject

class CalendarRepository @Inject constructor(
    private val api: GoogleCalendarApi
) {

    suspend fun getCalendarList(): Resource<CalendarList> {
        return try {
            val response = api.getCalendarList()
            val result = response.body()

            if (!response.isSuccessful) {
                Timber.d("Request unsuccessful: #${response.code()}")
            }

            if (response.isSuccessful && result != null) {
                Timber.d("Call successful")
                Timber.d(result.etag)
                Resource.Success(result)
            } else {
                Resource.Failure(response.message())
            }
        } catch (e: Exception) {
            Timber.d("Something went wrong: ${e.message}")
            Resource.Failure(e.stackTraceToString())
        }
    }


}