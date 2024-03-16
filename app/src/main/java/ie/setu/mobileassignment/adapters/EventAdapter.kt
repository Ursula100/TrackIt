package ie.setu.mobileassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mobileassignment.databinding.ViewEventBinding
import ie.setu.mobileassignment.models.EventModel

class EventAdapter (private var events: List<EventModel>) :
    RecyclerView.Adapter<EventAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = ViewEventBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val event = events[holder.adapterPosition]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    class MainHolder(private val binding : ViewEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel) {
            binding.viewEventTitle.text = event.title
            binding.viewEventDescription.text = event.description
            "${event.startTime} - ${event.endTime}".also { binding.viewEventTime.text = it }
        }
    }
}