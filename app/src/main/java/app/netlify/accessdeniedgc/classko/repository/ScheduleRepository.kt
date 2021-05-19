package app.netlify.accessdeniedgc.classko.repository

import app.netlify.accessdeniedgc.classko.database.ScheduleDao
import app.netlify.accessdeniedgc.classko.network.ClassKoApi
import app.netlify.accessdeniedgc.classko.network.Schedule
import app.netlify.accessdeniedgc.classko.network.ScheduleResponse
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import app.netlify.accessdeniedgc.classko.database.Schedule as ScheduleDB


class ScheduleRepository @Inject constructor(
    private val api: ClassKoApi,
    private val dao: ScheduleDao
) {

    val schedules = dao.getSchedules()

    suspend fun importSchedule(id: String): Resource<Schedule> {
        return try {
            val response = api.getSchedule(id)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else if (response.code() == 404) {
                Resource.Failure("Schedule not found")
            } else {
                Resource.Failure("Unexpected error: ${response.code()}")
            }
        } catch (e: UnknownHostException) {
            Resource.Failure("No internet")
        } catch (e: Exception) {
            Resource.Failure(e.localizedMessage!!)
        }
    }


    suspend fun exportSchedules(schedule: Schedule): Resource<ScheduleResponse> {
        return try {
            val response = api.addSchedule(schedule)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Failure(response.message())
            }
        } catch (e: UnknownHostException) {
            Resource.Failure("No internet")
        } catch (e: Exception) {
            Resource.Failure(e.localizedMessage!!)
        }
    }

    suspend fun insertSchedule(schedule: ScheduleDB): Long {
        return dao.insert(schedule)
    }

    suspend fun getSchedule(id: Long): ScheduleDB {
        return dao.getSchedule(id)
    }

    suspend fun updateSchedule(schedule: ScheduleDB) {
        dao.update(schedule)
    }

    suspend fun deleteSchedule(schedule: ScheduleDB) {
        dao.delete(schedule)
    }

    suspend fun clearDatabase() {
        dao.clear()
    }
}