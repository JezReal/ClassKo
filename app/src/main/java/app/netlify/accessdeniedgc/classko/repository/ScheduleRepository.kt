package app.netlify.accessdeniedgc.classko.repository

import app.netlify.accessdeniedgc.classko.model.Schedule
import app.netlify.accessdeniedgc.classko.network.ScheduleApi
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import java.lang.Exception
import java.net.UnknownHostException

import javax.inject.Inject


class ScheduleRepository @Inject constructor(
    private val api: ScheduleApi
) {

    suspend fun getSchedules(): Resource<List<Schedule>> {
        return try {
            val response = api.getScheduleList()
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Failure(response.message())
            }
        } catch (e: UnknownHostException) {
            Resource.Failure("No internet")
        } catch (e: Exception) {
            Resource.Failure(e.message!!)
        }
    }
}