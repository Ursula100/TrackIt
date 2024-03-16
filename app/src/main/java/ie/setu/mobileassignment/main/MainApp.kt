package ie.setu.mobileassignment.main

import android.app.Application
import ie.setu.mobileassignment.models.EventModel
import timber.log.Timber
import timber.log.Timber.i
import java.time.LocalDate

class MainApp : Application() {

    val events = ArrayList<EventModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("App started")

        events.add(EventModel("test 1", "", "Waterford", LocalDate.now(), LocalDate.now(), "9:10 AM", "20: 15 PM"))
        events.add(EventModel("test 2", "Whatever1", "", LocalDate.now(), LocalDate.now(), "", "20:15 PM"))
        events.add(EventModel("test 3", "Whatever2", "London", LocalDate.now(), LocalDate.now(), "9:10", ""))
        events.add(EventModel("test 1", "", "Waterford", LocalDate.now(), LocalDate.now(), "9:10 AM", "20: 15 PM"))
        events.add(EventModel("test 2", "Whatever1", "", LocalDate.now(), LocalDate.now(), "", "20:15 PM"))
        events.add(EventModel("test 3", "Whatever2", "London", LocalDate.now(), LocalDate.now(), "9:10", ""))
    }

}