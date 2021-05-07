package app.netlify.accessdeniedgc.classko.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val scheduleId: Long,

    @ColumnInfo(name = "subject_name")
    val subjectName: String,

    @ColumnInfo(name = "time_hour")
    val timeHour: Int,

    @ColumnInfo(name = "time_minute")
    val timeMinute: Int,

    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    val sunday: Boolean,
) {

    constructor(
        subjectName: String,

        timeHour: Int,

        timeMinute: Int,

        monday: Boolean,
        tuesday: Boolean,
        wednesday: Boolean,
        thursday: Boolean,
        friday: Boolean,
        saturday: Boolean,
        sunday: Boolean,
    ) : this(
        0,
        subjectName,
        timeHour,
        timeMinute,
        monday,
        tuesday,
        wednesday,
        thursday,
        friday,
        saturday,
        sunday
    )

    fun getDays(): String {
        var days = ""

        if (monday) {
            days += "Mon "
        }

        if (tuesday) {
            days += "Tue "
        }

        if (wednesday) {
            days += "Wed "
        }

        if (thursday) {
            days += "Thurs "
        }

        if (friday) {
            days += "Fri "
        }

        if (saturday) {
            days += "Sat "
        }

        if (sunday) {
            days += "Sun "
        }

        return days
    }
}