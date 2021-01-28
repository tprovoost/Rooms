package de.emil.rooms.private

import android.content.Intent
import android.os.Bundle
import android.view.View
import bindView
import de.emil.rooms.R
import de.emil.rooms.RoomActivity

class PrivateActivity : RoomActivity() {

    private val cardFamily: View by bindView(R.id.cardFamily)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cardFamily.setOnClickListener {
            startActivity(Intent(this, FamilyActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

    }

}