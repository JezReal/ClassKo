package app.netlify.accessdeniedgc.classko.ui.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import app.netlify.accessdeniedgc.classko.databinding.FragmentSignInBinding
import app.netlify.accessdeniedgc.classko.viewmodel.signin.SignInFragmentViewModel
import app.netlify.accessdeniedgc.classko.viewmodel.signin.SignInFragmentViewModel.SignInFragmentEvent.LaunchAccountChooser
import app.netlify.accessdeniedgc.classko.viewmodel.signin.SignInFragmentViewModel.SignInFragmentEvent.NavigateToClassFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var accountLauncher: ActivityResultLauncher<Intent>

    private val signInFragmentViewModel: SignInFragmentViewModel by viewModels()

    private lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        accountLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val bundle = result.data?.extras
//                    val account = bundle!!.get("googleSignInAccount") as GoogleSignInAccount
                    signInFragmentViewModel.navigateToClassFragment()
                } else {
                    //TODO: handle all possible errors
                    Timber.d("SignIn failed")
                    Snackbar.make(binding.root, "SignIn failed", Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)

        binding = FragmentSignInBinding.inflate(inflater, container, false)

        setUpListener()
        googleSignInOptions = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestEmail()
            .requestScopes(
                Scope("https://www.googleapis.com/auth/calendar"),
                Scope("https://www.googleapis.com/auth/calendar.events")
            )
            .build()
        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), googleSignInOptions)

        observeEvents()

        return binding.root
    }

    private fun observeEvents() {
        lifecycleScope.launchWhenStarted {
            signInFragmentViewModel.signInFragmentEvent.collect { event ->
                when (event) {
                    is LaunchAccountChooser -> {
                        handleSignIn()
                    }
                    is NavigateToClassFragment -> {
                        navigateToClassFragment()
                    }
                }
            }
        }
    }

    private fun setUpListener() {
        binding.signInButton.setOnClickListener {
            signInFragmentViewModel.launchAccountChooser()
        }
    }

    private fun handleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        accountLauncher.launch(signInIntent)
    }

    private fun navigateToClassFragment() {
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToNavigation())
    }
}