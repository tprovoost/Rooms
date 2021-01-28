package de.emil.rooms.professionals

import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import bindView
import de.emil.rooms.R

class Professionals : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professionals)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

}