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
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    fun addSchedule(schedule: Schedule, setupNotification: (Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            val id: Long = if (schedule.scheduleId > 0) {
                update(schedule)
                schedule.scheduleId
            } else {
                insert(schedule)
            }

            setupNotification(id)
        }
    }

    fun get(id: Long): LiveData<Schedule> {
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.getSchedule(id))
        }
    }

    private suspend fun update(schedule: Schedule) {
        repository.updateSchedule(schedule)
    }

    private suspend fun insert(schedule: Schedule): Long {
        return repository.insertSchedule(schedule)
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSchedule(schedule)
        }
    }
}