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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Trackit Activity started..")

        binding.addBtn.setOnClickListener {
            event.title = binding.eventTitle.text.toString()
            event.description = binding.eventDescription.text.toString()
            event.location = binding.eventLocation.text.toString()
            if (event.title.isNotEmpty()) {
                i("add Button Pressed: $event")
            }
            else {
                Snackbar
                    .make(it,"Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}