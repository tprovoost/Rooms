package de.emil.rooms.contacts

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import bindView
import de.emil.rooms.R
import de.emil.rooms.RoomActivity

class ContactsActivity : RoomActivity() {

    private val cardFamily: View by bindView(R.id.cardFamily)
    private val cardWork: View by bindView(R.id.cardWork)
    private val cardFriends: View by bindView(R.id.cardFriends)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cardFamily.setOnClickListener {
            startActivity(Intent(this, ContactsGridActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        cardWork.setOnClickListener {
            startActivity(Intent(this, ContactsGridActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        cardFriends.setOnClickListener {
            startActivity(Intent(this, ContactsGridActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

}