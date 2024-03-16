package ie.setu.mobileassignment.models

interface EventStore {
    fun findAll(): List<EventModel>
    fun create(event: EventModel)
}