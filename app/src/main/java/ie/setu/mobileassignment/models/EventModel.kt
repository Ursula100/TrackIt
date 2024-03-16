package ie.setu.mobileassignment.models

import java.time.LocalDate

data class EventModel(var title: String ="", var description: String = "", var location: String = "", var startDate: LocalDate, var endDate: LocalDate, var startTime: String, var endTime: String)
