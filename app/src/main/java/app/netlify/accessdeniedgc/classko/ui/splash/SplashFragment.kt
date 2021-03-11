package app.netlify.accessdeniedgc.classko.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.databinding.FragmentSplashBinding
import app.netlify.accessdeniedgc.classko.ui.`class`.ClassActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this.requireActivity())
        if (googleSignInAccount == null) {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
        } else {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToNavigation())
        }
    }
}