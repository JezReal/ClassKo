package app.netlify.accessdeniedgc.classko.repository.event

import app.netlify.accessdeniedgc.classko.model.event.EventsList
import app.netlify.accessdeniedgc.classko.network.GoogleCalendarApi
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import timber.log.Timber
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val api: GoogleCalendarApi
) {

    suspend fun getEventsList(id: String): Resource<EventsList> {
        return try {
            val response = api.getEventList(id)
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