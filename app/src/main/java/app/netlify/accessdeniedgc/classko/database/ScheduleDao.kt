package app.netlify.accessdeniedgc.classko.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScheduleDao {

    @Insert
    fun insert(schedule: Schedule)

    @Update
    fun update(schedule: Schedule)

    @Query("SELECT * FROM schedule_table ORDER BY scheduleId DESC")
    fun getSchedules(): LiveData<List<Schedule>>

    @Query("DELETE FROM schedule_table")
    fun clear()
}