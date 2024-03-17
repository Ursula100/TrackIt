package ie.setu.mobileassignment.models

import timber.log.Timber.i
import java.time.LocalDate

/**Class implementing a simple in-memory implementation using ArrayList
 */
class EventMemStore: EventStore {

    val events = ArrayList<EventModel>()

    //Method returns List of all events
    override fun findAll(): List<EventModel>{
        return events
    }

    //Method adds an event object to events store (arraylist of events)
    override fun create(event: EventModel){
        events.add(event)
        logAll()
    }

    //Method return list of events on that specified date.
    override fun findByDate(date: LocalDate): List<EventModel> {
        val eventsMatch = ArrayList<EventModel>()
        for(event in events){
            if(event.startDate == date) eventsMatch.add(event)
        }
        return eventsMatch
    }

    //Methods logs all events
    private fun logAll(){
        events.forEach { i("$it") }
    }
}