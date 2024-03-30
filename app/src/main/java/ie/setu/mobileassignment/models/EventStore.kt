package ie.setu.mobileassignment.models

import java.time.LocalDate
/**Class contains interface functions used to access placemark arraylist instead of doing so directly*/
interface EventStore {
    fun findAll(): List<EventModel>
    fun create(event: EventModel)
    fun findByDate(date: LocalDate): List<EventModel>
}