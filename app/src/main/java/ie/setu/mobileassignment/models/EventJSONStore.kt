package ie.setu.mobileassignment.models

import android.content.Context
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.setu.mobileassignment.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.time.LocalDate
import java.util.*

const val JSON_FILE = "events.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(LocalDate::class.java, LocalDateParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<EventModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class EventJSONStore(private val context: Context) : EventStore {

    private var events = mutableListOf<EventModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<EventModel> {
        logAll()
        return events
    }

    override fun create(event: EventModel) {
        event.id = generateRandomId()
        events.add(event)
        serialize()
    }

    override fun findByDate(date: LocalDate): List<EventModel> {
        val eventsMatch = ArrayList<EventModel>()
        for(event in events){
            if(event.startDate == date || event.endDate == date) eventsMatch.add(event)
        }
        return eventsMatch
    }


    override fun update(event: EventModel) {
        val eventFound: EventModel? = events.find { e -> e.id == event.id}
        if (eventFound != null){
            eventFound.title = event.title
            eventFound.description = event.description
            eventFound.location = event.location
            eventFound.startDate = event.startDate
            eventFound.endDate = event.endDate
            eventFound.startTime = event.startTime
            eventFound.endTime =  event.endTime
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(events, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        events = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        events.forEach { Timber.i("$it") }
    }
}

class LocalDateParser : JsonDeserializer<LocalDate>,JsonSerializer<LocalDate> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return LocalDate.parse(json?.asString)
    }

    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}