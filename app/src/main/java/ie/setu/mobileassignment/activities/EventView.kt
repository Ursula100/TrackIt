package ie.setu.mobileassignment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mobileassignment.databinding.ActivityEventViewBinding
import ie.setu.mobileassignment.databinding.ViewEventBinding
import ie.setu.mobileassignment.main.MainApp
import ie.setu.mobileassignment.models.EventModel

class EventView : AppCompatActivity() {

    private lateinit var binding: ActivityEventViewBinding
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        binding.dateTextView.text = "Friday, 16 March"

        binding.floatingAddButton.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EventAdapter(app.events)
    }
}

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
