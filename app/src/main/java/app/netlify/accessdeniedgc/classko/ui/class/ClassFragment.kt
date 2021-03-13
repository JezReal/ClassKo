package app.netlify.accessdeniedgc.classko.ui.`class`

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.databinding.FragmentClassBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import timber.log.Timber

class ClassFragment : Fragment() {

    private lateinit var binding: FragmentClassBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassBinding.inflate(layoutInflater, container, false)

        binding.logOutButton.setOnClickListener {
            //TODO: convert gso to hilt dependency
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(
                    Scope("https://www.googleapis.com/auth/calendar"),
                    Scope("https://www.googleapis.com/auth/calendar.events")
                )
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)

            googleSignInClient.signOut().addOnCompleteListener {
                Timber.d("You have been logged out")
            }

            navigateToSignInActivity()
        }

        binding.addEventFab.setOnClickListener {
            findNavController().navigate(ClassFragmentDirections.actionClassFragmentToAddEventFragment2())
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this.requireActivity())
        binding.emailAddress.text = googleSignInAccount!!.email
    }

    private fun navigateToSignInActivity() {
        findNavController().navigate(R.id.action_global_signInFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.log_out -> {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestScopes(
                        Scope("https://www.googleapis.com/auth/calendar"),
                        Scope("https://www.googleapis.com/auth/calendar.events")
                    )
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)

                googleSignInClient.signOut().addOnCompleteListener {
                    Timber.d("You have been logged out")
                }

                navigateToSignInActivity()
                true
            }
            else -> NavigationUI.onNavDestinationSelected(
                item,
                findNavController()
            ) || super.onOptionsItemSelected(item)
        }
    }
}