package app.netlify.accessdeniedgc.classko.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.netlify.accessdeniedgc.classko.databinding.FragmentAddEventBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddEventFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddEventBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(layoutInflater, container, false)

        return binding.root
    }
}