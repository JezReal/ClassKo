package app.netlify.accessdeniedgc.classko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import app.netlify.accessdeniedgc.classko.database.Schedule
import app.netlify.accessdeniedgc.classko.databinding.FragmentAddScheduleBinding
import app.netlify.accessdeniedgc.classko.util.formatTime
import app.netlify.accessdeniedgc.classko.viewmodel.AddScheduleViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddScheduleFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddScheduleBinding
    private lateinit var picker: MaterialTimePicker
    private val viewModel: AddScheduleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddScheduleBinding.inflate(inflater, container, false)

        buildTimePicker()
        setListeners()

        return binding.root
    }

    private fun buildTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setHour(12)
            .setMinute(0)
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setTitleText("Set event time")
            .build()

        picker.addOnPositiveButtonClickListener {
            binding.startTimeDisplay.text = formatTime(picker.hour, picker.minute)

        }

        picker.addOnNegativeButtonClickListener {

        }

        picker.addOnCancelListener {

        }
    }

    private fun setListeners() {
        binding.apply {

            subjectInput.addTextChangedListener {
                if (subjectInput.text.isNullOrEmpty()) {
                    subjectInputLayout.error = "This field is required"
                } else {
                    subjectInputLayout.error = null
                    subjectInputLayout.isErrorEnabled = false
                }
            }

            startTimeInputButton.setOnClickListener {
                buildTimePicker()
                picker.show(requireActivity().supportFragmentManager, "AddScheduleFragment")
            }

            addScheduleButton.setOnClickListener {
                if (validateInput()) {
                    val schedule = Schedule(
                        0,
                        binding.subjectInput.text.toString(),
                        picker.hour,
                        picker.minute,
                        binding.mondayCheckbox.isChecked,
                        binding.tuesdayCheckbox.isChecked,
                        binding.wednesdayCheckbox.isChecked,
                        binding.thursdayCheckbox.isChecked,
                        binding.fridayCheckbox.isChecked,
                        binding.saturdayCheckbox.isChecked,
                        binding.sundayCheckbox.isChecked,
                        )

                    viewModel.addSchedule(schedule)
                    dismiss()
                } else {
                    Snackbar.make(binding.root, "All fields are required", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        if (binding.subjectInputLayout.isErrorEnabled || !isCheckedBox() || !isTimeSet()) {
            return false
        }

        return true
    }

    private fun isCheckedBox(): Boolean {

        val checkBoxes = listOf(
            binding.mondayCheckbox.isChecked,
            binding.tuesdayCheckbox.isChecked,
            binding.wednesdayCheckbox.isChecked,
            binding.thursdayCheckbox.isChecked,
            binding.fridayCheckbox.isChecked,
            binding.saturdayCheckbox.isChecked,
            binding.sundayCheckbox.isChecked,
        )

        if (true in checkBoxes) {
            return true
        }

        return false
    }

    private fun isTimeSet(): Boolean {
        if (binding.startTimeDisplay.isVisible) {
            return true
        }

        return false
    }
}