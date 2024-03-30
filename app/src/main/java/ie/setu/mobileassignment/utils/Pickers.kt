package ie.setu.mobileassignment.utils

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK

object Pickers {

    fun datePicker(textTitle: String): MaterialDatePicker<Long>{
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        return MaterialDatePicker.Builder.datePicker()
                .setTitleText(textTitle)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build()
    }

    fun timePicker(textTitle: String, clockFormat: Int): MaterialTimePicker{

        return MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(10)
            .setMinute(15)
            .setInputMode(INPUT_MODE_CLOCK)
            .setTitleText(textTitle)
            .build()
    }

}