package app.netlify.accessdeniedgc.classko.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScheduleDao {

    @Insert
    fun insert(schedule: Schedule)

    @Update
    fun update(schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Query("SELECT * FROM schedule_table WHERE scheduleId = :id")
    fun getSchedule(id: Long): Schedule

    @Query("SELECT * FROM schedule_table ORDER BY scheduleId ASC")
    fun getSchedules(): LiveData<List<Schedule>>

    @Query("DELETE FROM schedule_table")
    fun clear()
}