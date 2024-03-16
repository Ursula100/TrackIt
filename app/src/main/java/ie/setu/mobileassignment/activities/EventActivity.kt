package ie.setu.mobileassignment.activities

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import ie.setu.mobileassignment.R
import ie.setu.mobileassignment.databinding.ActivityEventBinding
import ie.setu.mobileassignment.main.MainApp
import ie.setu.mobileassignment.models.EventModel
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


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

        val curDateTime = Calendar.getInstance().time
        val sFormatter = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
        val dFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd")
        val curDate = sFormatter.format(curDateTime)

        binding.startDateBtn.text = curDate
        binding.endDateBtn.text = curDate

        binding.startTimeBtn.text = getString(R.string._start_time)
        binding.endTimeBtn.text = getString(R.string._end_time)


        binding.addBtn.setOnClickListener {
            event.title = binding.eventTitle.text.toString()
            event.description = binding.eventDescription.text.toString()
            event.location = binding.eventLocation.text.toString()
            //var startDate = LocalDate.parse(binding.startDateBtn.text, dFormatter);
            //var endDate = LocalDate.parse(binding.endDateBtn.text, dFormatter);

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

        binding.startDateBtn.setOnClickListener(){
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select start date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(supportFragmentManager, "EventActivity")

            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                val selectedDate = sFormatter.format(selected)
                binding.startDateBtn.text = selectedDate
            }

        }

        binding.endDateBtn.setOnClickListener(){
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select start date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(supportFragmentManager, "EventActivity")

            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                val selectedDate = sFormatter.format(selected)
                binding.endDateBtn.text = selectedDate
            }
        }


        binding.startTimeBtn.setOnClickListener{

            val isSystem24Hour = is24HourFormat(this)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

            val timePicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(10)
                    .setMinute(15)
                    .setInputMode(INPUT_MODE_CLOCK)
                    .setTitleText("Set start time")
                    .build()

            timePicker.show(supportFragmentManager, "EventActivity")

            timePicker.addOnPositiveButtonClickListener {

                val pickedHour = timePicker.hour
                val pickedMinute = timePicker.minute

                val formattedTime: String = when{
                    pickedHour < 12 -> { if(pickedMinute < 10)  "$pickedHour:0$pickedMinute AM" else "$pickedHour:${pickedMinute} AM" }
                    else -> { if(pickedMinute < 10) "${pickedHour - 12}:0$pickedMinute PM" else "${pickedHour - 12}:${pickedMinute} PM" }
                }

                binding.startTimeBtn.text = formattedTime

            }

        }

        binding.endTimeBtn.setOnClickListener{

            val isSystem24Hour = is24HourFormat(this)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

            val timePicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(10)
                    .setMinute(15)
                    .setInputMode(INPUT_MODE_CLOCK)
                    .setTitleText("Set end time")
                    .build()

            timePicker.show(supportFragmentManager, "EventActivity")

            timePicker.addOnPositiveButtonClickListener {

                val pickedHour = timePicker.hour
                val pickedMinute = timePicker.minute

                val formattedTime: String = when{
                    pickedHour < 12 -> { if(pickedMinute < 10)  "$pickedHour:0$pickedMinute AM" else "$pickedHour:${pickedMinute} AM" }
                    else -> { if(pickedMinute < 10) "${pickedHour - 12}:0$pickedMinute PM" else "${pickedHour - 12}:${pickedMinute} PM" }
                }

                binding.endTimeBtn.text = formattedTime

            }

        }

    }
}