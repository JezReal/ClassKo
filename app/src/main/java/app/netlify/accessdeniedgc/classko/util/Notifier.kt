package app.netlify.accessdeniedgc.classko.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.broadcastreceiver.NotificationBroadcast
import app.netlify.accessdeniedgc.classko.database.Schedule
import timber.log.Timber
import java.util.*

object Notifier {

    private const val CHANNEL_ID = "Default"

    const val MONDAY = "MONDAY"
    const val TUESDAY = "TUESDAY"
    const val WEDNESDAY = "WEDNESDAY"
    const val THURSDAY = "THURSDAY"
    const val FRIDAY = "FRIDAY"
    const val SATURDAY = "SATURDAY"
    const val SUNDAY = "SUNDAY"
    const val SUBJECT_NAME = "SUBJECT NAME"
    const val IS_ACTIVE = "IS ACTIVE"
    const val ID = "ID"

    private const val RUN_DAILY: Long = 24 * 60 * 60 * 1000

    fun init(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                activity.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if (existingChannel == null) {
                // Create the NotificationChannel
                val name = "ClassKo Channel"
                val description = "Channel for notifications from ClassKo"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, name, importance)

                channel.description = description

                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun postNotification(id: Long, context: Context, subjectName: String) {

        Timber.d("notification id: $id")
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Time for class!")
            .setSmallIcon(R.drawable.ic_notif_icon)
            .setContentText(subjectName)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(id.toInt(), notification)
        }
    }

    fun scheduleNotification(schedule: Schedule, context: Context, id: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationBroadcast::class.java)

        intent.putExtra(MONDAY, schedule.monday)
        intent.putExtra(TUESDAY, schedule.tuesday)
        intent.putExtra(WEDNESDAY, schedule.wednesday)
        intent.putExtra(THURSDAY, schedule.thursday)
        intent.putExtra(FRIDAY, schedule.friday)
        intent.putExtra(SATURDAY, schedule.saturday)
        intent.putExtra(SUNDAY, schedule.sunday)
        intent.putExtra(SUBJECT_NAME, schedule.subjectName)
        intent.putExtra(ID, id)

        val pendingIntent = PendingIntent.getBroadcast(context, id.toInt(), intent, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, schedule.timeHour)
        calendar.set(Calendar.MINUTE, schedule.timeMinute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // if alarm time has already passed, increment day by 1
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            RUN_DAILY,
            pendingIntent
        )
    }

    fun cancelNotification(id: Long, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationBroadcast::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, id.toInt(), intent, 0)

        alarmManager.cancel(pendingIntent)
    }

    fun cancelAllNotifications(schedules: List<Schedule>, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationBroadcast::class.java)

        for (schedule in schedules) {
            val pendingIntent =
                PendingIntent.getBroadcast(context, schedule.scheduleId.toInt(), intent, 0)

            alarmManager.cancel(pendingIntent)
        }
    }
}