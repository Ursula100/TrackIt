package ie.setu.mobileassignment.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ie.setu.mobileassignment.adapters.EventAdapter
import ie.setu.mobileassignment.databinding.ActivityEventViewBinding
import ie.setu.mobileassignment.main.MainApp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventView : AppCompatActivity() {

    private lateinit var binding: ActivityEventViewBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val curLocalDate = LocalDate.now()
        val dFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy")
        val dFormatter1 = DateTimeFormatter.ofPattern("yyyy-M-d")
        val curDate = dFormatter.format(curLocalDate).substring(0,11)

        binding.dateTextView.text = curDate

        binding.recyclerView.adapter = EventAdapter(app.events.findByDate(curLocalDate))

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateString = "$year-${month+1}-$dayOfMonth"
            val localDate = LocalDate.parse(dateString, dFormatter1)
            val selectedDate = dFormatter.format(localDate).substring(0,11)
            binding.dateTextView.text = selectedDate
            binding.recyclerView.adapter = EventAdapter(app.events.findByDate(localDate))
        }

        binding.floatingAddButton.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            getResult.launch(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EventAdapter(app.events.findAll())
    }

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

}

