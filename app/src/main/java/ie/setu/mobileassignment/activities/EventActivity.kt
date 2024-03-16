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
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Trackit Activity started..")

        val curDateTime = Calendar.getInstance().time
        val sFormatter = SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault())
        val dFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy")
        val curDate = sFormatter.format(curDateTime)

        var selectedStartDate = curDate
        var selectedEndDate = curDate

        binding.startDateBtn.text = selectedStartDate.substring(0, 11)
        binding.endDateBtn.text = selectedEndDate.substring(0,11)

        binding.startTimeBtn.text = getString(R.string.default_start_time)
        binding.endTimeBtn.text = getString(R.string.default_end_time)


        binding.addBtn.setOnClickListener {
            val title = binding.eventTitle.text.toString()
            val description = binding.eventDescription.text.toString()
            val location = binding.eventLocation.text.toString()
            val startDate = LocalDate.parse(selectedStartDate, dFormatter)
            val endDate = LocalDate.parse(selectedEndDate, dFormatter)
            val startTime = binding.startTimeBtn.text.toString()
            val endTime = binding.endTimeBtn.text.toString()

            if (title.isNotEmpty()) {
                val event = EventModel(title, description, location, startDate, endDate, startTime, endTime)
                app.events.create(event.copy())
                i("add Button Pressed: $event")
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.startDateBtn.setOnClickListener{
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select start date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(supportFragmentManager, "EventActivity")

            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                selectedStartDate = sFormatter.format(selected)
                binding.startDateBtn.text = selectedStartDate.substring(0,11)
            }

        }

        binding.endDateBtn.setOnClickListener{
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select start date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(supportFragmentManager, "EventActivity")

            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                selectedEndDate = sFormatter.format(selected)
                binding.endDateBtn.text = selectedEndDate.substring(0, 11)
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