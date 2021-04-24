package app.netlify.accessdeniedgc.classko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.netlify.accessdeniedgc.classko.databinding.FragmentAddScheduleBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddScheduleFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddScheduleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddScheduleBinding.inflate(inflater, container, false)

        return binding.root
    }
}