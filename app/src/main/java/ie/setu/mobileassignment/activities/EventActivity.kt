package ie.setu.mobileassignment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.mobileassignment.databinding.ActivityEventBinding
import ie.setu.mobileassignment.main.MainApp
import ie.setu.mobileassignment.models.EventModel
import timber.log.Timber.i

class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    var event = EventModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Trackit Activity started..")

        binding.addBtn.setOnClickListener {
            event.title = binding.eventTitle.text.toString()
            event.description = binding.eventDescription.text.toString()
            event.location = binding.eventLocation.text.toString()
            if (event.title.isNotEmpty()) {
                app.events.add(event.copy())
                i("add Button Pressed: $event")
                for (i in app.events.indices){
                    i("Event[${i+1}]: ${app.events[i]}")
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