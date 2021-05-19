package app.netlify.accessdeniedgc.classko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import app.netlify.accessdeniedgc.classko.databinding.FragmentImportScheduleBinding
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImportScheduleFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentImportScheduleBinding
    private val viewModel: ScheduleListFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImportScheduleBinding.inflate(inflater, container, false)

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.importButton.setOnClickListener {
            if (binding.idInput.text?.isNotEmpty() == true) {
                viewModel.importSchedules(binding.idInput.text.toString())
                dismiss()
            }
        }
    }
}