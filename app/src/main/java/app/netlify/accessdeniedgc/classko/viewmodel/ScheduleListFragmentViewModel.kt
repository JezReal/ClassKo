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

    val scheduleList = repository.schedules

    sealed class ScheduleListFragmentState {
        object Empty : ScheduleListFragmentState()
        object Loading : ScheduleListFragmentState()
        class Success(val schedules: List<Schedule>) : ScheduleListFragmentState()
        class Failure(val message: String) : ScheduleListFragmentState()
    }

    sealed class ScheduleListFragmentEvent {

    }
}