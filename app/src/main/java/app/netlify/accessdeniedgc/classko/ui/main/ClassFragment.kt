package app.netlify.accessdeniedgc.classko.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.databinding.FragmentClassBinding
import app.netlify.accessdeniedgc.classko.viewmodel.`class`.ClassFragmentViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ClassFragment : Fragment() {

    private lateinit var binding: FragmentClassBinding

    @Inject
    lateinit var googleSignInOptions: GoogleSignInOptions
    private val classFragmentViewModel: ClassFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassBinding.inflate(layoutInflater, container, false)

        setListeners()
        observeEvents()
        getCalendar()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this.requireActivity())
        binding.emailAddress.text = googleSignInAccount!!.email
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(ClassFragmentDirections.actionGlobalSignInFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.log_out -> {
                classFragmentViewModel.logOut()
                true
            }
            else -> NavigationUI.onNavDestinationSelected(
                item,
                findNavController()
            ) || super.onOptionsItemSelected(item)
        }
    }

    private fun handleLogOut() {
        val googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), googleSignInOptions)

        googleSignInClient.signOut().addOnCompleteListener {
            Timber.d("You have been logged out")
        }

        navigateToSignInFragment()
    }

    private fun setListeners() {
        binding.addEventFab.setOnClickListener {
            classFragmentViewModel.addNewEvent()
        }
    }

    private fun observeEvents() {
        lifecycleScope.launchWhenStarted {
            classFragmentViewModel.classFragmentEvent.collect { event ->
                when (event) {
                    is ClassFragmentViewModel.ClassFragmentEvent.NavigateToAddEventFragment -> {
                        addEvent()
                    }
                    is ClassFragmentViewModel.ClassFragmentEvent.NavigateToSignInFragment -> {
                        showLogoutDialog()
                    }
                }
            }
        }
    }

    private fun addEvent() {
        findNavController().navigate(ClassFragmentDirections.actionClassFragmentToAddEventFragment())
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm log out?")
            .setMessage("Are you sure you  want to log out?")
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("OK") { dialog, which ->
                handleLogOut()
            }
            .show()
    }

    private fun getCalendar() {

    }
}