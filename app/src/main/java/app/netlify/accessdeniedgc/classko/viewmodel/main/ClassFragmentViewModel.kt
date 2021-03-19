package app.netlify.accessdeniedgc.classko.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.netlify.accessdeniedgc.classko.repository.calendar.CalendarRepository
import app.netlify.accessdeniedgc.classko.repository.event.EventRepository
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassFragmentViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _classFragmentState = MutableStateFlow<ClassFragmentState>(ClassFragmentState.Empty)
    val classFragmentState = _classFragmentState.asLiveData()

    private val _classFragmentEvent = Channel<ClassFragmentEvent>()
    val classFragmentEvent = _classFragmentEvent.receiveAsFlow()

    fun addNewEvent() {
        viewModelScope.launch(Dispatchers.Default) {
            _classFragmentEvent.send(ClassFragmentEvent.NavigateToAddEventFragment)
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.Default) {
            _classFragmentEvent.send(ClassFragmentEvent.NavigateToSignInFragment)
        }
    }

    fun loadCalendarList() {
        _classFragmentState.value = ClassFragmentState.Loading

        viewModelScope.launch(Dispatchers.Default) {
            when (val apiResponse = calendarRepository.getCalendarList()) {
                is Resource.Success -> {
                    _classFragmentState.value =
                        ClassFragmentState.Success(apiResponse.data!!)
                }
                is Resource.Failure -> {
                    _classFragmentState.value = ClassFragmentState.Failure(apiResponse.message!!)
                }
            }
        }
    }

    fun loadEventsList(id: String) {
        _classFragmentState.value = ClassFragmentState.Loading

        viewModelScope.launch(Dispatchers.Default) {
            when (val apiResponse = eventRepository.getEventsList(id)) {
                is Resource.Success -> {
                    _classFragmentState.value =
                        ClassFragmentState.Success(apiResponse.data!!)
                }
                is Resource.Failure -> {
                    _classFragmentState.value = ClassFragmentState.Failure(apiResponse.message!!)
                }
            }
        }
    }

    sealed class ClassFragmentState {
        object Empty : ClassFragmentState()
        object Loading : ClassFragmentState()
        class Success<T>(val data: T) : ClassFragmentState()
        class Failure(val message: String) : ClassFragmentState()
    }

    sealed class ClassFragmentEvent {
        object NavigateToAddEventFragment : ClassFragmentEvent()
        object NavigateToSignInFragment : ClassFragmentEvent()
    }
}