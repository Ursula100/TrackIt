package ie.setu.mobileassignment.main

import android.app.Application
import ie.setu.mobileassignment.models.EventModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp: Application() {

    val events = ArrayList<EventModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("App started")
    }

}