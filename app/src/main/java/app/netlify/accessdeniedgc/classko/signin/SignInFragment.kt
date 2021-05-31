package app.netlify.accessdeniedgc.classko.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import app.netlify.accessdeniedgc.classko.databinding.FragmentSigninBinding
import app.netlify.accessdeniedgc.classko.datastore.ClassKoDataStore
import app.netlify.accessdeniedgc.classko.signin.SignInViewModel.SignInFragmentEvent.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var job: Job
    @Inject
    lateinit var dataStore: ClassKoDataStore
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninBinding.inflate(inflater, container, false)

        setListeners()
        observeEvents()
        observeData()

        return binding.root
    }

    private fun setListeners() {
        binding.signinButton.setOnClickListener {
            if (validateInput()) {
                viewModel.authenticate(
                    binding.usernameInput.text.toString(),
                    binding.passwordInput.text.toString()
                )
            } else {
                Snackbar.make(binding.root, "Incomplete fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(): Boolean {

        if (binding.usernameInput.text.isNullOrEmpty() || binding.passwordInput.text.isNullOrEmpty()) {
            return false
        }

        return true
    }

    private fun observeData() {
        viewModel.token.observe(viewLifecycleOwner) {
            token = it
            Timber.d("Auth token: $it")
        }
    }

    private fun observeEvents() {
        job = viewModel.signInEvent.onEach { event ->
            when (event) {
                is AuthenticationFailure -> {
                    Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                }
                is AuthenticationSuccess -> {
                    dataStore.storeToken(token)
                    Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToScheduleListFragment())
                }
                else -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }
}