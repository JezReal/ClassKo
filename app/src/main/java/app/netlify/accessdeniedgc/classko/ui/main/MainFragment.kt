package app.netlify.accessdeniedgc.classko.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.netlify.accessdeniedgc.classko.databinding.FragmentMainBinding
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

    private fun setUpButtonListener() {
        binding.navigateToSignin.setOnClickListener {
            Timber.d("You navigated to signin")
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSignInActivity())
        }

        binding.navigateToClass.setOnClickListener {
            Timber.d("You navigated to classes")
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToClassActivity())
        }
    }
}