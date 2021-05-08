package app.netlify.accessdeniedgc.classko.service

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import app.netlify.accessdeniedgc.classko.repository.ScheduleRepository
import app.netlify.accessdeniedgc.classko.util.Notifier
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : LifecycleService() {

    @Inject
    lateinit var repository: ScheduleRepository

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Timber.d("notification service restarted")
        repository.schedules.observe(this) {
            for (schedule in it) {
                Notifier.scheduleNotification(
                    schedule,
                    this.applicationContext,
                    schedule.scheduleId
                )
            }
        }


        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)

        return null
    }
}