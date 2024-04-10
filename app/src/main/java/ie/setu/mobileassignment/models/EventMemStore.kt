package ie.setu.mobileassignment.models

import timber.log.Timber.i
import java.time.LocalDate

/**Class implementing a simple in-memory implementation using ArrayList
 */

var lastId = 0L
internal fun getId() = lastId++
class EventMemStore: EventStore {

    val events = ArrayList<EventModel>()

    //Method returns List of all events
    override fun findAll(): List<EventModel>{
        return events
    }

    //Method adds an event object to events store (arraylist of events)
    override fun create(event: EventModel){
        event.id = getId()
        events.add(event)
        logAll()
        lastId = event.id
    }

    //Method return list of events on that specified date.
    override fun findByDate(date: LocalDate): List<EventModel> {
        val eventsMatch = ArrayList<EventModel>()
        for(event in events){
            if(event.startDate == date) eventsMatch.add(event)
        }
        return eventsMatch
    }

    override fun update(event: EventModel){
        var eventFound: EventModel? = events.find { e -> e.id == event.id}
        if (eventFound != null){
            eventFound.title = event.title
            eventFound.description = event.description
            eventFound.location = event.location
            eventFound.startDate = event.startDate
            eventFound.endDate = event.endDate
            eventFound.startTime = event.startTime
            eventFound.endTime =  event.endTime
            logAll()
        }
    }

    //Method logs all events
    private fun logAll(){
        events.forEach { i("$it") }
    }
}