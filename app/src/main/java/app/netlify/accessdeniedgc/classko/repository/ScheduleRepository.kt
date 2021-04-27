package app.netlify.accessdeniedgc.classko.repository

import app.netlify.accessdeniedgc.classko.database.ScheduleDao
import app.netlify.accessdeniedgc.classko.model.Schedule
import app.netlify.accessdeniedgc.classko.network.ScheduleApi
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import java.net.UnknownHostException
import javax.inject.Inject
import app.netlify.accessdeniedgc.classko.database.Schedule as ScheduleDB


class ScheduleRepository @Inject constructor(
    private val api: ScheduleApi,
    private val dao: ScheduleDao
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

    suspend fun insertSchedule(schedule: ScheduleDB) {
        dao.insert(schedule)
    }

    val schedules = dao.getSchedules()
}