package ie.setu.mobileassignment.models

import timber.log.Timber.i
import java.time.LocalDate

class EventMemStore: EventStore {

    val events = ArrayList<EventModel>()

    override fun findAll(): List<EventModel>{
        return events
    }

    override fun create(event: EventModel){
        events.add(event)
        logAll()
    }

    override fun findByDate(date: LocalDate): List<EventModel> {
        val eventsMatch = ArrayList<EventModel>()
        for(event in events){
            if(event.startDate == date) eventsMatch.add(event)
        }
        return eventsMatch
    }

    fun logAll(){
        events.forEach { i("$it") }
    }
}