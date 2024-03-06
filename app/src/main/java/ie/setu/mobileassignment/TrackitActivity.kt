package ie.setu.mobileassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import timber.log.Timber.i

class TrackitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trackit)

        Timber.plant(Timber.DebugTree())
        i("Trackit Activity started..")

    }
}