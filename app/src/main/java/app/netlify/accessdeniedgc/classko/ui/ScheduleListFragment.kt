package app.netlify.accessdeniedgc.classko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.netlify.accessdeniedgc.classko.databinding.FragmentScheduleListBinding
import app.netlify.accessdeniedgc.classko.recyclerview.ScheduleAdapter
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel
import app.netlify.accessdeniedgc.classko.viewmodel.ScheduleListFragmentViewModel.ScheduleListFragmentState.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleListFragment : Fragment() {

    private lateinit var binding: FragmentScheduleListBinding
    private val viewModel: ScheduleListFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleListBinding.inflate(inflater)

        setUpListeners()
        observeData()

        return binding.root
    }

    private fun setUpListeners() {
        binding.addScheduleFab.setOnClickListener {
            findNavController().navigate(ScheduleListFragmentDirections.actionScheduleListFragmentToAddScheduleFragment())
        }
    }

    private fun observeData() {
        viewModel.scheduleList.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Empty -> {
                    viewModel.getSchedules()
                }
                is Loading -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    val adapter = ScheduleAdapter()
                    binding.recyclerView.adapter = adapter
                    adapter.submitList(state.schedules)
                }
                is Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    Snackbar.make(binding.root, state.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}