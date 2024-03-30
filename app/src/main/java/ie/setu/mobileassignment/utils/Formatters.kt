package ie.setu.mobileassignment.utils

object Formatters {

    //Formats time as a String such as: 8:05 AM/PM
    fun formattedTime(hour: Int, minute: Int): String = when{
        hour < 12 -> { if(minute < 10)  "$hour:0$minute AM" else "$hour:${minute} AM" }
        else -> { if(minute < 10) "${hour - 12}:0$minute PM" else "${hour - 12}:${minute} PM" }
    }
}