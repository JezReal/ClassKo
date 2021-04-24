package app.netlify.accessdeniedgc.classko.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.netlify.accessdeniedgc.classko.databinding.ScheduleItemBinding
import app.netlify.accessdeniedgc.classko.model.Schedule

class ScheduleAdapter : ListAdapter<Schedule, ScheduleAdapter.ViewHolder>(ScheduleDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(
        private val scheduleItemBinding: ScheduleItemBinding
    ) : RecyclerView.ViewHolder(scheduleItemBinding.root) {

        fun bind(item: Schedule) {
            scheduleItemBinding.subjectName.text = item.subject
            scheduleItemBinding.timeText.text = item.startTime + " - " + item.endTime
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ScheduleItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ScheduleDiffCallback : DiffUtil.ItemCallback<Schedule>() {
    override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule) = oldItem == newItem

}