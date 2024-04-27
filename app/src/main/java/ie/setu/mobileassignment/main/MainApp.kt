package ie.setu.mobileassignment.main

import android.app.Application
import ie.setu.mobileassignment.models.EventMemStore
import ie.setu.mobileassignment.models.EventModel
import timber.log.Timber
import timber.log.Timber.i
import java.time.LocalDate

class MainApp : Application() {

    lateinit var events: EventMemStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        events = EventMemStore()
        i("App started")
    }

}