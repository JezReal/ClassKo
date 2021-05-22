package app.netlify.accessdeniedgc.classko.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.ID
import android.widget.Toast
import app.netlify.accessdeniedgc.classko.service.NotificationService
import app.netlify.accessdeniedgc.classko.util.Notifier
import app.netlify.accessdeniedgc.classko.util.Notifier.FRIDAY
import app.netlify.accessdeniedgc.classko.util.Notifier.MONDAY
import app.netlify.accessdeniedgc.classko.util.Notifier.SATURDAY
import app.netlify.accessdeniedgc.classko.util.Notifier.SUBJECT_NAME
import app.netlify.accessdeniedgc.classko.util.Notifier.SUNDAY
import app.netlify.accessdeniedgc.classko.util.Notifier.THURSDAY
import app.netlify.accessdeniedgc.classko.util.Notifier.TUESDAY
import app.netlify.accessdeniedgc.classko.util.Notifier.WEDNESDAY
import timber.log.Timber
import java.util.*

class NotificationBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            scheduleNotifications(context)
        } else {
           
            if (isNotificationToday(intent)) {

                val id = intent?.getLongExtra(ID, -1)
                val subjectName = intent?.getStringExtra(SUBJECT_NAME) ?: "Subject name"

                Notifier.postNotification(id!!, context!!, subjectName)
            }
        }
    }


    private fun isNotificationToday(intent: Intent?): Boolean {
        val calendar = Calendar.getInstance()
        Timber.d("Current day is: ${calendar.get(Calendar.DAY_OF_WEEK)}")

        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> {
                val day = intent?.getBooleanExtra(MONDAY, false)
//                Timber.d("Monday: $day")
                return day == true
            }
            Calendar.TUESDAY -> {
                val day = intent?.getBooleanExtra(TUESDAY, false)
//                Timber.d("Tuesday: $day")
                return day == true
            }
            Calendar.WEDNESDAY -> {
                val day = intent?.getBooleanExtra(WEDNESDAY, false)
//                Timber.d("Wednesday: $day")
                return day == true
            }
            Calendar.THURSDAY -> {
                val day = intent?.getBooleanExtra(THURSDAY, false)
//                Timber.d("Thursday: $day")
                return day == true
            }
            Calendar.FRIDAY -> {
                val day = intent?.getBooleanExtra(FRIDAY, false)
//                Timber.d("Friday: $day")
                return day == true
            }
            Calendar.SATURDAY -> {
                val day = intent?.getBooleanExtra(SATURDAY, false)
//                Timber.d("Saturday: $day")
                return day == true
            }
            Calendar.SUNDAY -> {
                val day = intent?.getBooleanExtra(SUNDAY, false)
//                Timber.d("Sunday: $day")
                return day == true
            }
        }

        return false
    }

    private fun scheduleNotifications(context: Context?) {
        val intent = Intent(context, NotificationService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(intent)
        } else {
            context?.startService(intent)
        }
    }
}