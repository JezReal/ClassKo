package app.netlify.accessdeniedgc.classko.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.database.Schedule
import app.netlify.accessdeniedgc.classko.databinding.FragmentScheduleListBinding
import app.netlify.accessdeniedgc.classko.recyclerview.ScheduleAdapter
import app.netlify.accessdeniedgc.classko.util.Notifier
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScheduleListFragment : Fragment() {

    private lateinit var binding: FragmentScheduleListBinding
    private val viewModel: ScheduleListFragmentViewModel by viewModels()
    private lateinit var scheduleList: List<Schedule>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleListBinding.inflate(inflater, container, false)

        setUpListeners()
        observeData()

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
            else -> {
                NavigationUI.onNavDestinationSelected(
                    item,
                    findNavController()
                ) || super.onOptionsItemSelected(item)
            }
        }
    }
}