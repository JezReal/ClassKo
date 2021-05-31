package app.netlify.accessdeniedgc.classko.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.netlify.accessdeniedgc.classko.signin.SignInViewModel.SignInFragmentEvent.*
import app.netlify.accessdeniedgc.classko.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: SigninRepository
) : ViewModel() {


    private val _signInEvent = Channel<SignInFragmentEvent>(Channel.BUFFERED)
    val signInEvent = _signInEvent.receiveAsFlow()

    private val _token = MutableStateFlow("")
    val token = _token.asLiveData()

    fun authenticate(username: String, password: String) {
        viewModelScope.launch {
            _signInEvent.send(Loading)
        }


        viewModelScope.launch {
            when (val apiResponse = repository.authenticate(username, password)) {
                is Resource.Success -> {
                    _token.value = apiResponse.data!!
                    _signInEvent.send(AuthenticationSuccess("Login success"))
                }
                is Resource.Failure -> {
                    _signInEvent.send(AuthenticationFailure(apiResponse.message!!))
                }
            }

        }
    }


    sealed class SignInFragmentEvent {
        object Loading : SignInFragmentEvent()
        class AuthenticationSuccess(val message: String) : SignInFragmentEvent()
        class AuthenticationFailure(val message: String) : SignInFragmentEvent()
    }
}