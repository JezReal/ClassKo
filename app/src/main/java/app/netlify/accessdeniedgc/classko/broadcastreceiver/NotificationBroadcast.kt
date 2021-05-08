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
            Timber.d("Broadcast receiver hit")
            if (isNotificationToday(intent)) {
                val id = intent?.getLongExtra(ID, -1)
                val subjectName = intent?.getStringExtra(SUBJECT_NAME) ?: "Subject name"

                Notifier.postNotification(id!!, context!!, subjectName)
            }
        }
    }

    private fun isNotificationToday(intent: Intent?): Boolean {
        val calendar = Calendar.getInstance()

        when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> {
                return intent?.getBooleanExtra(SUNDAY, false) == true
            }
            Calendar.MONDAY -> {
                return intent?.getBooleanExtra(MONDAY, false) == true
            }
            Calendar.TUESDAY -> {
                return intent?.getBooleanExtra(TUESDAY, false) == true
            }
            Calendar.WEDNESDAY -> {
                return intent?.getBooleanExtra(WEDNESDAY, false) == true
            }
            Calendar.THURSDAY -> {
                return intent?.getBooleanExtra(THURSDAY, false) == true
            }
            Calendar.FRIDAY -> {
                return intent?.getBooleanExtra(FRIDAY, false) == true
            }
            Calendar.SATURDAY -> {
                return intent?.getBooleanExtra(SATURDAY, false) == true
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