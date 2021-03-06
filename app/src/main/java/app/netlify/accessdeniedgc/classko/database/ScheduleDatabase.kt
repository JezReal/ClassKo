package app.netlify.accessdeniedgc.classko.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Schedule::class],
    version = 2,
    exportSchema = false
)
abstract class ScheduleDatabase : RoomDatabase() {

    abstract val scheduleDao: ScheduleDao
}