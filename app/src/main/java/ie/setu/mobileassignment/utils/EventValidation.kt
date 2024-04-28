package ie.setu.mobileassignment.utils

import java.time.LocalDate

object EventValidation {

    /*Method compares two time strings, str1 and str2 in the format Hour:Minute AM/PM
      Returns 1 if str1 is before str2 and 0 otherwise
     */
    fun timeCompare(str1: String, str2: String): Int{
        val amPm1 = str1.substring(str1.indexOf(' ') + 1)
        val amPm2 = str2.substring(str2.indexOf(' ') + 1)
        return if(amPm1.compareTo(amPm2) != 0)
            if (amPm1 == "AM")  1 else 0
        else {
            val sHour1 = Integer.parseInt(str1.substring(0, str1.indexOf(':')))
            val sHour2 = Integer.parseInt(str2.substring(0, str2.indexOf(':')))
            if (sHour1 == sHour2) {
                val sMinute1 = Integer.parseInt(str1.substring(str1.indexOf(':') + 1, str1.indexOf(':') + 3))
                val sMinute2 = Integer.parseInt(str2.substring(str2.indexOf(':') + 1, str2.indexOf(':') + 3))
                if (sMinute1 <= sMinute2) 1 else 0
            }
            else if (sHour1 < sHour2) 1
            else 0
        }
    }


    /*Method compares two localDates, d1 and d2
      Returns false if the second localDate, d2 comes before the first, d1.
      Returns true if d2 is the same or later than d1
     */
    fun datesValid(d1: LocalDate, d2 : LocalDate) : Boolean {
        return !d2.isBefore(d1)
    }

}