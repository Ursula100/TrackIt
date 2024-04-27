package ie.setu.mobileassignment.activities

import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.TimeFormat
import ie.setu.mobileassignment.R
import ie.setu.mobileassignment.databinding.ActivityEventBinding
import ie.setu.mobileassignment.main.MainApp
import ie.setu.mobileassignment.models.EventModel
import ie.setu.mobileassignment.utils.EventValidation.datesValid
import ie.setu.mobileassignment.utils.EventValidation.timeCompare
import ie.setu.mobileassignment.utils.Formatters.formattedTime
import ie.setu.mobileassignment.utils.Pickers.datePicker
import ie.setu.mobileassignment.utils.Pickers.timePicker
import timber.log.Timber.i
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Activity in charge of activity_event xml layout.
 *It manages the visual screen to create events
 * It inherits the android life cycle from AppCompatActivity.
 */
class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    private var event = EventModel(0,"", "", "", LocalDate.now(), LocalDate.now(), "", "")
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        var edit = false //
        var eventId = 0L

        i("Trackit Activity started..") /***TODO: extract all log messages to string.xml*/

        //A formatter which Converts a Date object to desired String representation.
        val sFormatter = SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault())
        //Formatter for parsing DateTime objects to desired string representation.
        val dFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy")

        //Get today's date as LocalDAte object
        val curLocalDate = LocalDate.now()
        //Convert today's date to String of format of format: Sat, SEP 12 2024
        val curDate = curLocalDate.format(dFormatter)

        /*Assign curDate string to both start and end date as default.
          It's important to have it in full i.e including the year, as we may need to parse it to some date type*/
        var selectedStartDate = curDate
        var selectedEndDate = curDate

        //Get substring od date string as Sat, SEP 12 (11 first chars)
        binding.startDateBtn.text = selectedStartDate.substring(0, 11)
        binding.endDateBtn.text = selectedEndDate.substring(0,11)

        //Assign default start and end times as string values from Resource folder.
        binding.startTimeBtn.text = getString(R.string.default_start_time)
        binding.endTimeBtn.text = getString(R.string.default_end_time)

        if (intent.hasExtra("event_edit")){
            edit = true
            event = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.extras?.getParcelable("event_edit", EventModel::class.java)!!//Find link to code snippet
            } else intent.extras?.getParcelable("event_edit")!!
            eventId = event.id
            binding.eventTitle.setText(event.title)
            binding.eventDescription.setText(event.description)
            binding.eventLocation.setText(event.location)
            binding.startDateBtn.text = event.startDate.format(dFormatter).substring(0,11)
            selectedStartDate = event.startDate.format(dFormatter)
            selectedEndDate =  event.endDate.format(dFormatter)
            binding.endDateBtn.text = event.endDate.format(dFormatter).substring(0,11)
            binding.startTimeBtn.text = event.startTime
            binding.endTimeBtn.text = event.endTime

            binding.addBtn.setText(R.string.button_save_event)
        }


        /* Listener detects when addBtn is clicked.
           When addBtn is clicked, f the title field is left empty, it shows a quick message is shown to the user telling them to enter a title.
           Else, an event is created and the created event is added to the arraylist with appropriate values for each field.
         */
        binding.addBtn.setOnClickListener {
            val title = binding.eventTitle.text.toString()
            val description = binding.eventDescription.text.toString()
            val location = binding.eventLocation.text.toString()
            val startDate = LocalDate.parse(selectedStartDate, dFormatter) //Parse String to LocalDate using DateTimeFormatter
            val endDate = LocalDate.parse(selectedEndDate, dFormatter)
            val startTime = binding.startTimeBtn.text.toString()
            val endTime = binding.endTimeBtn.text.toString()

            if (title.isNotEmpty() && datesValid(startDate, endDate) && timeCompare(startTime,endTime) == 1) {
                val event = EventModel(eventId, title, description, location, startDate, endDate, startTime, endTime)
                if(edit) {
                    app.events.update(event.copy())
                    i("Save Event Button Pressed: $event")
                }
                else {
                    app.events.create(event.copy())
                    i("add Button Pressed: $event")
                }
                setResult(RESULT_OK)
                finish()
            }
            else if (title.isEmpty()){
                Snackbar
                    .make(it, getString(R.string.enter_a_title), Snackbar.LENGTH_LONG)
                    .show()
            }
            else if (!datesValid(startDate, endDate)){
                Snackbar
                    .make(it, getString(R.string.date_selection_error), Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                Snackbar
                    .make(it, getString(R.string.time_selection_error), Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        /*Date picker pops up when button is clicked.
          Retrieves and stores date selected from date picker as start date.
          https://github.com/material-components/material-components-android/blob/master/docs/components/DatePicker.md
        */
        binding.startDateBtn.setOnClickListener{

            val pickerTitle = getString(R.string.select_start_date)
            val datePicker = datePicker(pickerTitle)

            datePicker.show(supportFragmentManager, "EventActivity")

            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                selectedStartDate = sFormatter.format(selected) //Includes year details
                binding.startDateBtn.text = selectedStartDate.substring(0,11)
            }

        }

        /*Date picker pops up when button is clicked.
          Retrieves and stores date selected from date picker as end date.
        */
        binding.endDateBtn.setOnClickListener{
            val pickerTitle = getString(R.string.select_end_date)

            val datePicker = datePicker(pickerTitle)

            datePicker.show(supportFragmentManager, "EventActivity")

            datePicker.addOnPositiveButtonClickListener {
                val selected = datePicker.selection
                selectedEndDate = sFormatter.format(selected) //Includes year details
                binding.endDateBtn.text = selectedEndDate.substring(0, 11)
            }
        }


        /*Time picker pops up when button is clicked.
          Retrieves and stores date selected from time picker as a String of form: 8:05 AM/PM
        */
        binding.startTimeBtn.setOnClickListener{

            val isSystem24Hour = is24HourFormat(this)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

            val pickerTitle = getString(R.string.set_start_time)

            //Programmatically creates time picker
            val timePicker = timePicker(pickerTitle, clockFormat)

            timePicker.show(supportFragmentManager, "EventActivity") //display time picker

            timePicker.addOnPositiveButtonClickListener {

                val pickedHour = timePicker.hour
                val pickedMinute = timePicker.minute

                binding.startTimeBtn.text = formattedTime(pickedHour, pickedMinute)

            }

        }

        /*Time picker pops up when button is clicked.
          Retrieves and stores date selected from time picker as a String of form: 8:05 AM/PM
        */
        binding.endTimeBtn.setOnClickListener{

            val isSystem24Hour = is24HourFormat(this)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

            val pickerTitle = getString(R.string.set_end_time)

            //Programmatically creates time picker
            val timePicker = timePicker(pickerTitle, clockFormat)

            timePicker.show(supportFragmentManager, "EventActivity")

            //When clicked on ok, the hour selected is stored and used
            timePicker.addOnPositiveButtonClickListener {

                val pickedHour = timePicker.hour
                val pickedMinute = timePicker.minute

                binding.endTimeBtn.text = formattedTime(pickedHour, pickedMinute)

            }

        }
    }
}
