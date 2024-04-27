package ie.setu.mobileassignment.main

import android.app.Application
import ie.setu.mobileassignment.models.EventJSONStore
import ie.setu.mobileassignment.models.EventStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var events: EventStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //events = EventMemStore()
        events = EventJSONStore(applicationContext)
        i("App started")
    }

}