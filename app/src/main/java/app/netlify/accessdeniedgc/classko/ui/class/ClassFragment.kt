package app.netlify.accessdeniedgc.classko.ui.`class`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.netlify.accessdeniedgc.classko.databinding.FragmentClassBinding

class ClassFragment : Fragment() {

    private lateinit var binding: FragmentClassBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClassBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}