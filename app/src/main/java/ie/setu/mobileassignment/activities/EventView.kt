package ie.setu.mobileassignment.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ie.setu.mobileassignment.adapters.EventAdapter
import ie.setu.mobileassignment.adapters.EventListener
import ie.setu.mobileassignment.databinding.ActivityEventViewBinding
import ie.setu.mobileassignment.main.MainApp
import ie.setu.mobileassignment.models.EventModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventView : AppCompatActivity(), EventListener {

    private lateinit var binding: ActivityEventViewBinding
    lateinit var app: MainApp

    //Binds activity to the layout
    //Initialises some components when the activity is created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        // Assigns current date as LocalDate object
        val curLocalDate = LocalDate.now()
        //Formatter parses LocalDate object to a String representation
        val dFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy")
        val dFormatter1 = DateTimeFormatter.ofPattern("yyyy-M-d")

        //Get current date in like: Sat, SEP 11
        val curDate = dFormatter.format(curLocalDate).substring(0,11)

        //Display today's date at first as default
        binding.dateTextView.text = curDate

        //RecyclerView displays events on selected date
        binding.recyclerView.adapter = EventAdapter(app.events.findByDate(curLocalDate), this)

        //Listens to change of selected date, updates shown selected date text and updates RecyclerView with events of the current selected date
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateString = "$year-${month+1}-$dayOfMonth"
            val localDate = LocalDate.parse(dateString, dFormatter1)
            val selectedDate = dFormatter.format(localDate).substring(0,11)
            binding.dateTextView.text = selectedDate
            binding.recyclerView.adapter = EventAdapter(app.events.findByDate(localDate), this)
        }

        //Add button launches EventActivity when clicked and  keeps note of result
        binding.floatingAddButton.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            getResult.launch(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EventAdapter(app.events.findAll(), this)
    }

    //If result from EventActivity is RESULT_CANCELED notify user that the event was not created
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(
                    0,
                    app.events.findAll().size
                )
            }
            if (it.resultCode == Activity.RESULT_CANCELED) {
                Snackbar.make(binding.root, "Adding Event Cancelled", Snackbar.LENGTH_LONG).show()
            }
        }

    override fun onEventClick(event: EventModel) {
        val launcherIntent = Intent(this, EventActivity::class.java)
        getResult.launch(launcherIntent)
    }

}

