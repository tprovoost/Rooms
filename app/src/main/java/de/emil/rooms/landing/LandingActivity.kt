package de.emil.rooms.landing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import bindView
import de.emil.rooms.MainActivity
import de.emil.rooms.R
import de.emil.rooms.RoomActivity
import de.emil.rooms.interests.ChooseInterestActivity
import de.emil.rooms.contacts.ContactsActivity
import de.emil.rooms.professionals.ProfessionalActivity

class LandingActivity : RoomActivity() {

    private val startBtn: View by bindView(R.id.startBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        Log.d("TEST", "Debug")

        startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}