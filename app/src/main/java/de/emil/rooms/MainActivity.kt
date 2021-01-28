package de.emil.rooms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import bindView
import de.emil.rooms.interests.ChooseInterestActivity
import de.emil.rooms.private.ContactsActivity
import de.emil.rooms.professionals.ProfessionalActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RoomActivity() {

    private val cardPrivate: View by bindView(R.id.cardPrivate)
    private val cardProfessional: View by bindView(R.id.cardProfesionnals)
    private val cardInterests: View by bindView(R.id.cardInterests)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        Log.d("TEST", "Debug")

        cardPrivate.setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        cardProfessional.setOnClickListener {
            startActivity(Intent(this, ProfessionalActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        cardInterests.setOnClickListener {
            startActivity(Intent(this, ChooseInterestActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}