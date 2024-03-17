package ie.setu.mobileassignment.models

import java.time.LocalDate

interface EventStore {
    fun findAll(): List<EventModel>
    fun create(event: EventModel)
    fun findByDate(date: LocalDate): List<EventModel>
}