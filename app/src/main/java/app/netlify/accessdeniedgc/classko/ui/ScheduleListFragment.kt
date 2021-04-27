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
        //TODO: add button to clear local database then add edit and delete? functionality
        val adapter = ScheduleAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.scheduleList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}