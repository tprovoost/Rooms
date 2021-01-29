package de.emil.rooms.interests

import android.content.Intent
import android.os.Bundle
import android.view.View
import bindView
import de.emil.rooms.R
import de.emil.rooms.RoomActivity
import de.emil.rooms.professionals.ProfessionalListActivity.CategoriesEnum

class InterestsActivity: RoomActivity() {

    private val cardHealth: View by bindView(R.id.cardHealth)
    private val cardCraft: View by bindView(R.id.cardCraft)
    private val cardFood: View by bindView(R.id.cardFood)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professionals)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cardHealth.setOnClickListener {
            startActivity(Intent(this, InterestsGridActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        cardCraft.setOnClickListener {
            startActivity(Intent(this, InterestsGridActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        cardFood.setOnClickListener {
            startActivity(Intent(this, InterestsGridActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

}