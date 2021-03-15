package app.netlify.accessdeniedgc.classko.viewmodel.`class`

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ClassFragmentViewModel : ViewModel() {

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

    sealed class ClassFragmentEvent {
        object NavigateToAddEventFragment : ClassFragmentEvent()
        object NavigateToSignInFragment : ClassFragmentEvent()
    }
}