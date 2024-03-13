package ie.setu.mobileassignment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.mobileassignment.databinding.ActivityEventBinding
import ie.setu.mobileassignment.models.EventModel
import timber.log.Timber
import timber.log.Timber.i

class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    var event = EventModel()
    var events = ArrayList<EventModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i("Trackit Activity started..")

        binding.addBtn.setOnClickListener {
            event.title = binding.eventTitle.text.toString()
            event.description = binding.eventDescription.text.toString()
            event.location = binding.eventLocation.text.toString()
            if (event.title.isNotEmpty()) {
                events.add(event.copy())
                i("add Button Pressed: $event")
                for (i in events.indices){
                    i("Event[${i+1}: ${events[i]}")
                }
            }
            else {
                Snackbar
                    .make(it,"Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}