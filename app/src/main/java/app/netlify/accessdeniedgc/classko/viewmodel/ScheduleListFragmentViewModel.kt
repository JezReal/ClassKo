package app.netlify.accessdeniedgc.classko.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.netlify.accessdeniedgc.classko.model.Schedule
import app.netlify.accessdeniedgc.classko.repository.ScheduleRepository
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel.ScheduleListFragmentState.*
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleListFragmentViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {

    private val _scheduleList =
        MutableStateFlow<ScheduleListFragmentState>(Empty)
    val scheduleList = _scheduleList.asLiveData()

    fun getSchedules() {
        _scheduleList.value = Loading

        viewModelScope.launch(Dispatchers.Default) {
            when (val scheduleList = repository.getSchedules()) {
                is Resource.Success -> {
                    _scheduleList.value = Success(scheduleList.data!!)
                }

                is Resource.Failure -> {
                    _scheduleList.value = Failure(scheduleList.message!!)
                }
            }
        }
    }

    sealed class ScheduleListFragmentState {
        object Empty : ScheduleListFragmentState()
        object Loading : ScheduleListFragmentState()
        class Success(val schedules: List<Schedule>) : ScheduleListFragmentState()
        class Failure(val message: String) : ScheduleListFragmentState()
    }

    sealed class ScheduleListFragmentEvent {

    }
}