package app.netlify.accessdeniedgc.classko.ui

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.database.Schedule
import app.netlify.accessdeniedgc.classko.databinding.FragmentScheduleListBinding
import app.netlify.accessdeniedgc.classko.recyclerview.ScheduleAdapter
import app.netlify.accessdeniedgc.classko.util.Notifier
import app.netlify.accessdeniedgc.classko.viewmodel.AddScheduleViewModel
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel.ScheduleListFragmentEvent.*
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel.ScheduleListFragmentState.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class ScheduleListFragment : Fragment() {

    private lateinit var binding: FragmentScheduleListBinding
    private val viewModel: ScheduleListFragmentViewModel by activityViewModels()
    private val addScheduleViewModel: AddScheduleViewModel by activityViewModels()
    private lateinit var scheduleList: List<Schedule>
    private lateinit var job: Job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleListBinding.inflate(inflater, container, false)

        setUpListeners()
        observeData()
        observeState()
        observeEvents()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        job = viewModel.scheduleEvent
            .onEach { event ->
                when (event) {
                    is ShowSnackBar -> {
                        showSnackBar(event.message)
                    }
                    is ShowExportDialog -> {
                        showExportDialog(event.id)
                    }
                    is AddSchedulesToDatabase -> {
                        addSchedulesToDatabase(event.schedule)
                    }
                    is ExportSuccess -> {
                        viewModel.showExportDialog(event.response.id)
                    }
                    is ExportFailure -> {
                        viewModel.showSnackBar(event.message)
                    }
                    is ImportSuccess -> {
                        viewModel.addSchedulesToDatabase(event.response)
                    }
                    is ImportFailure -> {
                        viewModel.showSnackBar(event.message)
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }

    private fun setUpListeners() {
        binding.addScheduleFab.setOnClickListener {
            findNavController().navigate(ScheduleListFragmentDirections.actionScheduleListFragmentToAddScheduleFragment())
        }
    }

    private fun observeData() {
        val adapter = ScheduleAdapter { schedule ->
            findNavController().navigate(
                ScheduleListFragmentDirections.actionScheduleListFragmentToAddScheduleFragment(
                    schedule.scheduleId
                )
            )
        }

        binding.recyclerView.adapter = adapter
        viewModel.scheduleList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }

            if (it.isEmpty()) {
                binding.guideText.isVisible = true
            } else {
                binding.recyclerView.isVisible = true
                binding.guideText.isVisible = false
            }

            scheduleList = it
        }
    }

    private fun observeState() {
//        viewModel.scheduleState.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is Loading -> {
//                    viewModel.showSnackBar("Loading...")
//                }
//                else -> {
//
//                }
//            }
//        }
    }

    private fun observeEvents() {

    }

    private fun addSchedulesToDatabase(schedule: app.netlify.accessdeniedgc.classko.network.Schedule) {
        schedule.scheduleItems.map {
            val newSchedule = Schedule(
                it.subjectName,
                it.timeHour,
                it.timeMinute,
                it.monday,
                it.tuesday,
                it.wednesday,
                it.thursday,
                it.friday,
                it.saturday,
                it.sunday
            )

            addScheduleViewModel.addSchedule(newSchedule) { id ->
                Notifier.scheduleNotification(
                    newSchedule,
                    requireContext().applicationContext,
                    id
                )
            }
        }
    }

    private fun exportSchedules() {
        if (scheduleList.isEmpty()) {
            Snackbar.make(binding.root, "Cannot export empty data", Snackbar.LENGTH_SHORT).show()
        } else {
            viewModel.exportSchedules(scheduleList)
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showExportDialog(responseId: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Success")
        builder.setMessage("Id is: $responseId")
        builder.setPositiveButton("Copy to clipboard") { _, _ ->
            val clipBoard =
                requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val scheduleId = ClipData.newPlainText("id", responseId)
            clipBoard.setPrimaryClip(scheduleId)

            Snackbar.make(binding.root, "Copied to clipboard", Snackbar.LENGTH_SHORT).show()
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_database -> {
                viewModel.clearDatabase()
                Notifier.cancelAllNotifications(scheduleList, requireContext().applicationContext)
                true
            }
            R.id.import_schedules -> {
                findNavController().navigate(ScheduleListFragmentDirections.actionScheduleListFragmentToImportScheduleDialog())
                true
            }
            R.id.export_schedules -> {
                exportSchedules()
                true
            }
            else -> {
                NavigationUI.onNavDestinationSelected(
                    item,
                    findNavController()
                ) || super.onOptionsItemSelected(item)
            }
        }
    }
}