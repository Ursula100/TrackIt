package ie.setu.mobileassignment.activities

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
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
import java.util.Locale

/** Activity in charge of activity_event xml layout.
 *It manages the visual screen to create events
 * It inherits the android life cycle from AppCompatActivity.
 */
class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Trackit Activity started..")

        //A formatter which Converts a Date object to desired String representation.
        val sFormatter = SimpleDateFormat("EEE, MMM dd yyyy", Locale.getDefault())
        //Formatter for parsing DateTime objects to desired string representation.
        val dFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy")

        //Get today's date as LocalDAte object
        val curLocalDate = LocalDate.now()
        //Convert today's date to String of format of format Sat. SEP 12 2024
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

            /*Method compares two time strings, str1 and str2 in the format Hour:Minute AM/PM
              Returns 1 if str1 is before str2 and 0 otherwise
            */

            fun timeCompare(str1: String, str2: String): Int{
                val amPm1 = str1.substring(str1.indexOf(' ') + 1)
                val amPm2 = str2.substring(str2.indexOf(' ') + 1)
                return if(amPm1 !== amPm2)
                    if (amPm1 == "AM")  1 else 0
                else {
                    val sHour1 = str1.substring(0, str1.indexOf(':'))
                    val sHour2 = str2.substring(0, str2.indexOf(':'))
                    if (sHour1 == sHour2) {
                        val sMinute1 = str1.substring(str1.indexOf(':') + 1, str1.indexOf(':') + 2)
                        val sMinute2 = str2.substring(str2.indexOf(':') + 1, str2.indexOf(':') + 2)
                        if (sMinute1 <= sMinute2) 1 else 0
                    } else if (sHour1 < sHour2) 1
                    else 0
                }
            }

            if (title.isNotEmpty() && startDate <= endDate && timeCompare(startTime,endTime) == 1) {
                val event = EventModel(title, description, location, startDate, endDate, startTime, endTime)
                app.events.create(event.copy())
                i("add Button Pressed: $event")
                setResult(RESULT_OK)
                finish()
            }
            else if (title.isEmpty()){
                Snackbar
                    .make(it,"Please enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
            else if (timeCompare(startTime, endTime) == 0){
                Snackbar
                    .make(it,"End time should be no earlier than the start time ", Snackbar.LENGTH_LONG)
                    .show()
            }
            else {
                Snackbar
                    .make(it,"End date should be no earlier than the start date", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        /*Date picker pops up when button is clicked.
          Retrieves and stores date selected from date picker as start date.
          https://github.com/material-components/material-components-android/blob/master/docs/components/DatePicker.md
        */
        binding.startDateBtn.setOnClickListener{

            val constraintsBuilder =
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select start date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(constraintsBuilder.build())
                    .build()

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

            val constraintsBuilder =
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select start date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(constraintsBuilder.build())
                    .build()

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

            //Programmatically creates time picker
            val timePicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(10)
                    .setMinute(15)
                    .setInputMode(INPUT_MODE_CLOCK)
                    .setTitleText("Set start time")
                    .build()

            timePicker.show(supportFragmentManager, "EventActivity") //display time picker

            timePicker.addOnPositiveButtonClickListener {

                val pickedHour = timePicker.hour
                val pickedMinute = timePicker.minute

                //Formats time as a String such as: 8:05 AM/PM
                val formattedTime: String = when{
                    pickedHour < 12 -> { if(pickedMinute < 10)  "$pickedHour:0$pickedMinute AM" else "$pickedHour:${pickedMinute} AM" }
                    else -> { if(pickedMinute < 10) "${pickedHour - 12}:0$pickedMinute PM" else "${pickedHour - 12}:${pickedMinute} PM" }
                }

                binding.startTimeBtn.text = formattedTime

            }

        }


        /*Time picker pops up when button is clicked.
          Retrieves and stores date selected from time picker as a String of form: 8:05 AM/PM
        */
        binding.endTimeBtn.setOnClickListener{

            val isSystem24Hour = is24HourFormat(this)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

            //Programmatically creates time picker
            val timePicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(10) //Hour hand on 10 as default
                    .setMinute(15) //Minute hand on 15 as default
                    .setInputMode(INPUT_MODE_CLOCK) //Start in clock mode
                    .setTitleText("Set end time")
                    .build()

            timePicker.show(supportFragmentManager, "EventActivity")

            //When clicked on ok, the hour selected is stored and used
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