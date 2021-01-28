package de.emil.rooms.professionals

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import bindView
import de.emil.rooms.R
import de.emil.rooms.private.ContactsGridActivity
import de.emil.rooms.professionals.ProfessionalListActivity.CategoriesEnum

class ProfessionalActivity: AppCompatActivity() {

    private val cardHealth: View by bindView(R.id.cardHealth)
    private val cardCraft: View by bindView(R.id.cardCraft)
    private val cardFood: View by bindView(R.id.cardFood)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professionals)
        setSupportActionBar(findViewById(R.id.toolbar))

        cardHealth.setOnClickListener {
            val intent = ProfessionalListActivity.newIntent(this, CategoriesEnum.HEALTH)
            startActivity(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        cardCraft.setOnClickListener {
            val intent = ProfessionalListActivity.newIntent(this, CategoriesEnum.CRAFT)
            startActivity(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        cardFood.setOnClickListener {
            val intent = ProfessionalListActivity.newIntent(this, CategoriesEnum.FOOD)
            startActivity(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

}