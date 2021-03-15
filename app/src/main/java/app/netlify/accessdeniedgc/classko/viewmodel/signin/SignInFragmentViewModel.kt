package app.netlify.accessdeniedgc.classko.viewmodel.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class SignInFragmentViewModel : ViewModel() {

    private val _signInFragmentEvent = Channel<SignInFragmentEvent>()
    val signInFragmentEvent = _signInFragmentEvent.receiveAsFlow()

    fun navigateToClassFragment() {
        viewModelScope.launch(Dispatchers.Default) {
            _signInFragmentEvent.send(SignInFragmentEvent.NavigateToClassFragment)
        }
    }

    fun launchAccountChooser() {
        viewModelScope.launch(Dispatchers.Default) {
            _signInFragmentEvent.send(SignInFragmentEvent.LaunchAccountChooser)
        }
    }

    sealed class SignInFragmentEvent {
        object NavigateToClassFragment : SignInFragmentEvent()
        object LaunchAccountChooser : SignInFragmentEvent()
        class SignInFailed(val message: String) : SignInFragmentEvent()
    }
}