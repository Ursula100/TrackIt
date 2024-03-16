package ie.setu.mobileassignment.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.mobileassignment.adapters.EventAdapter
import ie.setu.mobileassignment.databinding.ActivityEventViewBinding
import ie.setu.mobileassignment.main.MainApp

class EventView : AppCompatActivity() {

    private lateinit var binding: ActivityEventViewBinding
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        binding.dateTextView.text = "Friday, 16 March"

        binding.floatingAddButton.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = EventAdapter(app.events.findAll())
    }
}

