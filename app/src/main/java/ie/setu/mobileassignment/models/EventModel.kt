package ie.setu.mobileassignment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class EventModel(var id: Long = 0,
                      var title: String,
                      var description: String,
                      var location: String,
                      var startDate: LocalDate,
                      var endDate: LocalDate,
                      var startTime: String,
                      var endTime: String) : Parcelable
