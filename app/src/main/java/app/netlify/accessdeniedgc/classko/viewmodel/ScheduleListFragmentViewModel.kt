package app.netlify.accessdeniedgc.classko.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.netlify.accessdeniedgc.classko.network.Schedule
import app.netlify.accessdeniedgc.classko.network.ScheduleItem
import app.netlify.accessdeniedgc.classko.network.ScheduleResponse
import app.netlify.accessdeniedgc.classko.repository.ScheduleRepository
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel.ScheduleListFragmentEvent.*
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel.ScheduleListFragmentState.*
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import app.netlify.accessdeniedgc.classko.database.Schedule as ScheduleDB

@HiltViewModel
class ScheduleListFragmentViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _scheduleState =
        MutableStateFlow<ScheduleListFragmentState>(Empty)
    val scheduleState = _scheduleState.asLiveData()

    private val _scheduleEvent = Channel<ScheduleListFragmentEvent>(Channel.BUFFERED)
    val scheduleEvent = _scheduleEvent.receiveAsFlow()

    val scheduleList = repository.schedules

    val userSchedules = repository.userSchedules

    fun clearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearDatabase()
        }
    }

    fun exportSchedules(token: String, scheduleDB: List<ScheduleDB>) {
        viewModelScope.launch {
            _scheduleEvent.send(ShowSnackBar("Loading..."))
        }


        val scheduleItems = ArrayList<ScheduleItem>()

        scheduleDB.map {
            if (it.type == null) {
                scheduleItems.add(
                    ScheduleItem(
                        it.subjectName,
                        it.type,
                        it.timeHour,
                        it.timeMinute,
                        it.monday,
                        it.tuesday,
                        it.wednesday,
                        it.thursday,
                        it.friday,
                        it.saturday,
                        it.sunday
                    )
                )
            }
        }

        val schedule = Schedule(scheduleItems)

        viewModelScope.launch(Dispatchers.Default) {
            when (val apiResponse = repository.exportSchedules(token, schedule)) {
                is Resource.Success -> {
                    _scheduleEvent.send(ExportSuccess(apiResponse.data!!))
                }
                is Resource.Failure -> {
                    _scheduleEvent.send(ExportFailure(apiResponse.message!!))
                }
            }
        }
    }

    fun importSchedules(token: String, id: String) {

        viewModelScope.launch {
            _scheduleEvent.send(ShowSnackBar("Loading..."))
        }

        viewModelScope.launch(Dispatchers.Default) {
            when (val apiResponse = repository.importSchedule(token, id)) {
                is Resource.Success -> {
                    _scheduleEvent.send(ImportSuccess(apiResponse.data!!))
                }
                is Resource.Failure -> {
                    _scheduleEvent.send(ImportFailure(apiResponse.message!!))
                }
            }
        }
    }

    fun showSnackBar(message: String) {
        viewModelScope.launch {
            _scheduleEvent.send(ShowSnackBar(message))
        }
    }

    fun showExportDialog(id: String) {
        viewModelScope.launch {
            _scheduleEvent.send(ShowExportDialog(id))
        }
    }

    fun addSchedulesToDatabase(schedule: Schedule) {
        viewModelScope.launch {
            _scheduleEvent.send(AddSchedulesToDatabase(schedule))
        }
    }

    fun getClassSchedules(token: String) {
        viewModelScope.launch {
            _scheduleEvent.send(ShowSnackBar("Loading class schedule"))
        }

        viewModelScope.launch(Dispatchers.Default) {
            when (val apiResponse = repository.getClassSchedules(token)) {
                is Resource.Success -> {
                    _scheduleEvent.send(GetClassScheduleSuccess(apiResponse.data!!))
                }
                is Resource.Failure -> {
                    _scheduleEvent.send(GetClassScheduleFailure(apiResponse.message!!))
                }
            }
        }
    }

    fun deleteClassSchedules() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteClassSchedules()
        }
    }

    sealed class ScheduleListFragmentState {
        object Empty : ScheduleListFragmentState()
    }

    sealed class ScheduleListFragmentEvent {
        class ShowSnackBar(val message: String) : ScheduleListFragmentEvent()
        class ShowExportDialog(val id: String) : ScheduleListFragmentEvent()
        class AddSchedulesToDatabase(val schedule: Schedule) : ScheduleListFragmentEvent()
        class ExportSuccess(val response: ScheduleResponse) : ScheduleListFragmentEvent()
        class ExportFailure(val message: String) : ScheduleListFragmentEvent()
        class ImportSuccess(val response: Schedule) : ScheduleListFragmentEvent()
        class ImportFailure(val message: String) : ScheduleListFragmentEvent()
        class GetClassScheduleSuccess(val classSchedules: Schedule) : ScheduleListFragmentEvent()
        class GetClassScheduleFailure(val message: String) : ScheduleListFragmentEvent()
    }
}