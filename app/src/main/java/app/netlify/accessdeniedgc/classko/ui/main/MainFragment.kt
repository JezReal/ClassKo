package app.netlify.accessdeniedgc.classko.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.databinding.FragmentMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.CalendarScopes.CALENDAR
import com.google.api.services.calendar.model.Event
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        setUpButtonListener()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this.requireActivity())
        if (googleSignInAccount == null) {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSignInActivity())
        } else {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToClassActivity())
        }
    }

    private fun setUpButtonListener() {
//        binding.navigateToSignin.setOnClickListener {
//            Timber.d("You navigated to signin")
//
//            findNavController().navigate(
//                MainFragmentDirections.actionMainFragmentToSignInActivity()
//            )
//        }
//
//        binding.navigateToClass.setOnClickListener {
//            Timber.d("You navigated to classes")
//
//            findNavController().navigate(
//                MainFragmentDirections.actionMainFragmentToClassActivity()
//            )
//        }
    }
}