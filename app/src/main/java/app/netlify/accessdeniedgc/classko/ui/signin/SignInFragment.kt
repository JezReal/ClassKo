package app.netlify.accessdeniedgc.classko.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.netlify.accessdeniedgc.classko.databinding.FragmentSignInBinding
import timber.log.Timber

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)

        setUpListener()
        return binding.root
    }

    private fun setUpListener() {
        binding.signInButton.setOnClickListener {
            Timber.d("You clicked the sign in button")
        }
    }
}