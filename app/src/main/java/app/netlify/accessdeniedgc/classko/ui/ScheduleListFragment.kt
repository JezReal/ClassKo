package app.netlify.accessdeniedgc.classko.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.database.Schedule
import app.netlify.accessdeniedgc.classko.databinding.FragmentScheduleListBinding
import app.netlify.accessdeniedgc.classko.recyclerview.ScheduleAdapter
import app.netlify.accessdeniedgc.classko.util.Notifier
import app.netlify.accessdeniedgc.classko.viewmodel.AddScheduleViewModel
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel.ScheduleListFragmentState.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ScheduleListFragment : Fragment() {

    private lateinit var binding: FragmentScheduleListBinding
    private val viewModel: ScheduleListFragmentViewModel by activityViewModels()
    private val addScheduleViewModel: AddScheduleViewModel by activityViewModels()
    private lateinit var scheduleList: List<Schedule>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleListBinding.inflate(inflater, container, false)

        setUpListeners()
        observeData()
        observeState()

        setHasOptionsMenu(true)

        return binding.root
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
            scheduleList = it
        }
    }

    private fun observeState() {
        viewModel.scheduleState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setView(R.layout.dialog_loading)
                        .create()
                        .show()
                }
                is ExportSuccess -> {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setView(R.layout.dialog_export_success)
                        .create()
                        .show()
                    Timber.d("export success")
                }
                is ExportFailure -> {
                    Timber.d("export failed: ${state.message}")
                }
                is ImportSuccess -> {
                    addSchedulesToDatabase(state.response)
                }
                is ImportFailure -> {
                    Timber.d("import failed: ${state.message}")
                }
                else -> {

                }
            }
        }
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
        viewModel.exportSchedules(scheduleList)
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