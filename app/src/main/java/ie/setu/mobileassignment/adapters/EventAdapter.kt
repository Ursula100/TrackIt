package ie.setu.mobileassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mobileassignment.databinding.ViewEventBinding
import ie.setu.mobileassignment.models.EventModel

/** This adapter class helps connect event data to the RecyclerView
 * It also determines which view holder will be used to display the data, in this case found view_event.xml
 */

interface EventListener {
    fun onEventClick(event: EventModel)
}

class EventAdapter (private var events: List<EventModel>, private val listener: EventListener) :
    RecyclerView.Adapter<EventAdapter.MainHolder>() {

    //Initialises the viewHolder when the adapter is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = ViewEventBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    //Binds the viewHolder to the adapter.
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val event = events[holder.adapterPosition]
        holder.bind(event, listener)
    }

    //Method returns the size of the event collection. Will use subsequently
    override fun getItemCount(): Int = events.size

    class MainHolder(private val binding : ViewEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventModel, listener: EventListener) {
            binding.viewEventTitle.text = event.title
            binding.viewEventDescription.text = event.description
            "${event.startTime} - ${event.endTime}".also { binding.viewEventTime.text = it }
            binding.root.setOnClickListener{ listener.onEventClick(event)}
        }
    }
}