package ie.setu.mobileassignment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.setu.mobileassignment.R
import ie.setu.mobileassignment.main.MainApp

class EventView : AppCompatActivity() {

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_view)
        app = application as MainApp
    }
}