package ie.setu.mobileassignment.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import timber.log.Timber.i
import java.time.LocalDate

// SQLite database constants
private const val DATABASE_NAME = "events.db"
private const val TABLE_NAME = "events"
private const val COLUMN_ID = "id"
private const val COLUMN_TITLE = "title"
private const val COLUMN_DESCRIPTION = "description"
private const val COLUMN_LOCATION = "location"
private const val COLUMN_STARTDATE = "startDate"
private const val COLUMN_ENDDATE = "endDate"
private const val COLUMN_STARTTIME = "startTime"
private const val COLUMN_ENDTIME = "endTime"


class EventSQLStore(context: Context) : EventStore {

    private var database: SQLiteDatabase

    init {
        // Set up local database connection
        database = EventDbHelper(context).writableDatabase
    }

    override fun findAll(): List<EventModel> {
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = database.rawQuery(query, null)

        val events = ArrayList<EventModel>()

        cursor.use {
            while (it.moveToNext()) {
                events.add(
                    //getEventFromCursor(it))
                    EventModel(
                        id = cursor.getLong(0),
                        title = cursor.getString(1),
                        description = cursor.getString(2),
                        location = cursor.getString(3),
                        startDate = LocalDate.parse(cursor.getString(4)),
                        endDate = LocalDate.parse(cursor.getString(5)),
                        startTime = cursor.getString(6),
                        endTime = cursor.getString(7)
                    )
                )
            }
        }

        i("eventsdb : findAll() - has ${events.size} events")
        return events
    }

    override fun findByDate(date: LocalDate): List<EventModel> {
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = database.rawQuery(query, null)

        val events = ArrayList<EventModel>()

        cursor.use {
            while(it.moveToNext()){
                if (LocalDate.parse(it.getString(4)).isEqual(date) || LocalDate.parse(it.getString(5)).isEqual(date)){
                    events.add(
                        //getEventFromCursor(it))
                        EventModel(
                            id = cursor.getLong(0),
                            title = cursor.getString(1),
                            description = cursor.getString(2),
                            location = cursor.getString(3),
                            startDate = LocalDate.parse(cursor.getString(4)),
                            endDate = LocalDate.parse(cursor.getString(5)),
                            startTime = cursor.getString(6),
                            endTime = cursor.getString(7)
                        )
                    )
                }
            }
        }
        i("eventsdb : findByDate($date) - has ${events.size} events")
        return events
    }

    override fun create(event: EventModel) {
        val values = ContentValues()

        values.put(COLUMN_TITLE, event.title)
        values.put(COLUMN_DESCRIPTION, event.description)
        values.put(COLUMN_LOCATION, event.location)
        values.put(COLUMN_STARTDATE, event.startDate.toString())
        values.put(COLUMN_ENDDATE, event.endDate.toString())
        values.put(COLUMN_STARTTIME, event.startTime)
        values.put(COLUMN_ENDTIME, event.endTime)

        event.id = database.insert(TABLE_NAME, null, values)

        i("New Event: $event")
    }

    override fun update(event: EventModel) {
        val values = ContentValues()
        val filter = "id=" + event.id

        values.put(COLUMN_TITLE, event.title)
        values.put(COLUMN_DESCRIPTION, event.description)
        values.put(COLUMN_LOCATION, event.location)
        values.put(COLUMN_STARTDATE, event.startDate.toString())
        values.put(COLUMN_ENDDATE, event.endDate.toString())
        values.put(COLUMN_STARTTIME, event.startTime)
        values.put(COLUMN_ENDTIME, event.endTime)

        database.update(TABLE_NAME, values, filter, null)

        val events = findAll()

        events.forEach { i("$it") }

    }

}

private class EventDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    private val createTableSQL =
        "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_LOCATION TEXT, " +
                "$COLUMN_STARTDATE DATE, $COLUMN_ENDDATE DATE, $COLUMN_STARTTIME TEXT, $COLUMN_ENDTIME TEXT)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema upgrades if needed
    }
}