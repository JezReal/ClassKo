package app.netlify.accessdeniedgc.classko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import app.netlify.accessdeniedgc.classko.databinding.FragmentImportScheduleBinding
import app.netlify.accessdeniedgc.classko.datastore.ClassKoDataStore
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImportScheduleFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentImportScheduleBinding
    private val viewModel: ScheduleListFragmentViewModel by activityViewModels()
    @Inject
    lateinit var dataStore: ClassKoDataStore
    private lateinit var token: String

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

        dataStore.tokenFlow.asLiveData().observe(viewLifecycleOwner) {
            token = it
        }

        binding.importButton.setOnClickListener {
            if (binding.idInput.text?.isNotEmpty() == true) {
                viewModel.importSchedules(token, binding.idInput.text.toString())
                dismiss()
            }
        }
    }
}