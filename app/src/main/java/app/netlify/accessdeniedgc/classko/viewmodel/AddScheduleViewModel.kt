package app.netlify.accessdeniedgc.classko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import app.netlify.accessdeniedgc.classko.database.Schedule
import app.netlify.accessdeniedgc.classko.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    private var scheduleLiveData: LiveData<Schedule>? = null

    fun addSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            if (schedule.scheduleId > 0) {
                Timber.d("Existing sched")
                update(schedule)
            } else {
                Timber.d("New schedule")
                insert(schedule)
            }
        }
    }

    fun get(id: Long): LiveData<Schedule> {
        return scheduleLiveData
            ?: liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(repository.getSchedule(id))
            }.also {
                scheduleLiveData = it
            }
    }

    private suspend fun update(schedule: Schedule) {
        repository.updateSchedule(schedule)
    }

    private suspend fun insert(schedule: Schedule) {
        repository.insertSchedule(schedule)
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSchedule(schedule)
        }
    }
}