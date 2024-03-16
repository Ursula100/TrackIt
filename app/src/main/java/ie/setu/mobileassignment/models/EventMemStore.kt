package ie.setu.mobileassignment.models

import timber.log.Timber.i

class EventMemStore: EventStore {

    val events = ArrayList<EventModel>()

    override fun findAll(): List<EventModel>{
        return events
    }

    override fun create(event: EventModel){
        events.add(event)
        logAll()
    }

    fun logAll(){
        events.forEach { i("$it") }
    }
}