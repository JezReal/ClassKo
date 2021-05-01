package app.netlify.accessdeniedgc.classko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import app.netlify.accessdeniedgc.classko.database.Schedule
import app.netlify.accessdeniedgc.classko.databinding.FragmentAddScheduleBinding
import app.netlify.accessdeniedgc.classko.util.formatTime
import app.netlify.accessdeniedgc.classko.viewmodel.AddScheduleViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddScheduleFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddScheduleBinding
    private lateinit var picker: MaterialTimePicker
    private val viewModel: AddScheduleViewModel by viewModels()
    private lateinit var currentState: CurrentState
    private val args: AddScheduleFragmentArgs by navArgs()

    private enum class CurrentState {
        NEW_SCHEDULE,
        EXISTING_SCHEDULE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddScheduleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setState()
        buildTimePicker()
        setListeners()
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

        var schedule: Schedule? = null
        var hourDb = 0
        var minuteDb = 0

        if (currentState == CurrentState.EXISTING_SCHEDULE) {
            viewModel.get(args.scheduleId).observe(viewLifecycleOwner) { scheduleItem ->
                binding.subjectInput.setText(scheduleItem.subjectName)
                binding.startTimeDisplay.text =
                    formatTime(scheduleItem.timeHour, scheduleItem.timeMinute)

                binding.apply {
                    subjectInput.setText(scheduleItem.subjectName)
                    startTimeDisplay.text =
                        formatTime(scheduleItem.timeHour, scheduleItem.timeMinute)

                    if (scheduleItem.monday) {
                        mondayCheckbox.isChecked = true
                    }
                    if (scheduleItem.tuesday) {
                        tuesdayCheckbox.isChecked = true
                    }
                    if (scheduleItem.wednesday) {
                        wednesdayCheckbox.isChecked = true
                    }
                    if (scheduleItem.thursday) {
                        thursdayCheckbox.isChecked = true
                    }
                    if (scheduleItem.friday) {
                        fridayCheckbox.isChecked = true
                    }
                    if (scheduleItem.saturday) {
                        saturdayCheckbox.isChecked = true
                    }
                    if (scheduleItem.sunday) {
                        sundayCheckbox.isChecked = true
                    }
                }

                //store time from database to prevent errors when editing a schedule
                hourDb = scheduleItem.timeHour
                minuteDb = scheduleItem.timeMinute

                schedule = scheduleItem
            }

            binding.deleteButton.isVisible = true
            binding.deleteButton.setOnClickListener {
                viewModel.deleteSchedule(schedule!!)

                dismiss()
            }
        }

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

            doneButton.setOnClickListener {
                if (validateInput()) {

                    var hour: Int
                    var minute: Int

                    try {
                        // if user did not change the time when editing a schedule, this will throw a NPE
                        // and if that happens, we just get the data from the database

                        hour = picker.hour
                        minute = picker.minute
                    } catch (e: NullPointerException) {
                        hour = hourDb
                        minute = minuteDb
                    }

                    val newSchedule = Schedule(
                        schedule?.scheduleId ?: 0,
                        binding.subjectInput.text.toString(),
                        hour,
                        minute,
                        binding.mondayCheckbox.isChecked,
                        binding.tuesdayCheckbox.isChecked,
                        binding.wednesdayCheckbox.isChecked,
                        binding.thursdayCheckbox.isChecked,
                        binding.fridayCheckbox.isChecked,
                        binding.saturdayCheckbox.isChecked,
                        binding.sundayCheckbox.isChecked,
                    )

                    viewModel.addSchedule(newSchedule)
                    dismiss()
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

    private fun setState() {
        currentState = if (args.scheduleId > 0) CurrentState.EXISTING_SCHEDULE
        else CurrentState.NEW_SCHEDULE
    }
}