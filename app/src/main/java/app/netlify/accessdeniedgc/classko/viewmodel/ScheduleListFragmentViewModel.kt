package app.netlify.accessdeniedgc.classko.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.netlify.accessdeniedgc.classko.network.Schedule
import app.netlify.accessdeniedgc.classko.network.ScheduleItem
import app.netlify.accessdeniedgc.classko.network.ScheduleResponse
import app.netlify.accessdeniedgc.classko.repository.ScheduleRepository
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel.ScheduleListFragmentEvent.ShowSnackBar
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

    fun clearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearDatabase()
        }
    }

    fun exportSchedules(scheduleDB: List<ScheduleDB>) {
        _scheduleState.value = Loading

        val scheduleItems = ArrayList<ScheduleItem>()

        scheduleDB.map {
            scheduleItems.add(
                ScheduleItem(
                    it.subjectName,
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

        val schedule = Schedule(scheduleItems)

        viewModelScope.launch(Dispatchers.Default) {
            when (val apiResponse = repository.exportSchedules(schedule)) {
                is Resource.Success -> {
                    _scheduleEvent.send(ExportSuccess(apiResponse.data!!))
                }
                is Resource.Failure -> {
                    _scheduleEvent.send(ExportFailure(apiResponse.message!!))
                }
            }
        }
    }

    fun importSchedules(id: String) {

        _scheduleState.value = Loading
        viewModelScope.launch(Dispatchers.Default) {
            when (val apiResponse = repository.importSchedule(id)) {
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

    sealed class ScheduleListFragmentState {
        object Empty : ScheduleListFragmentState()
        object Loading : ScheduleListFragmentState()
        class ExportSuccess(val response: ScheduleResponse) : ScheduleListFragmentState()
        class ExportFailure(val message: String) : ScheduleListFragmentState()
        class ImportSuccess(val response: Schedule) : ScheduleListFragmentState()
        class ImportFailure(val message: String) : ScheduleListFragmentState()
    }

    sealed class ScheduleListFragmentEvent {
        class ShowSnackBar(val message: String) : ScheduleListFragmentEvent()
        class ShowExportDialog(val id: String) : ScheduleListFragmentEvent()
        class AddSchedulesToDatabase(val schedule: Schedule) : ScheduleListFragmentEvent()
        class ExportSuccess(val response: ScheduleResponse) : ScheduleListFragmentEvent()
        class ExportFailure(val message: String) : ScheduleListFragmentEvent()
        class ImportSuccess(val response: Schedule) : ScheduleListFragmentEvent()
        class ImportFailure(val message: String) : ScheduleListFragmentEvent()
    }
}