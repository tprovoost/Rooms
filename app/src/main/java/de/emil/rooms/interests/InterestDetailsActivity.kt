package de.emil.rooms.interests

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bindView
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import de.emil.rooms.Data
import de.emil.rooms.R

class InterestDetailsActivity : AppCompatActivity() {

    private val callBtn: ImageButton by bindView(R.id.callBtn)
    private val messageBtn: ImageButton by bindView(R.id.messageBtn)
    private val nameTV: TextView by bindView(R.id.cardContactTV)
    private val descriptionTV: TextView by bindView(R.id.cardDescriptionTV)
    private val contactIV: ImageView by bindView(R.id.cardContactIV)
    private val background: View by bindView(R.id.container)

    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTransition()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        position = intent.getIntExtra(EXTRA_POSITION, 0)

        initViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupTransition() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // Set the transition name, which matches Activity A’s start view transition name, on
        // the root view.
        findViewById<View>(android.R.id.content).transitionName = "shared_element_image"

        // Attach a callback used to receive the shared elements from Activity A to be
        // used by the container transform transition.
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        // Set this Activity’s enter and return transition to a MaterialContainerTransform
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 300L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 250L
        }
    }

    private fun initViews() {
        val contact = Data.interestsContactValues[position]

        nameTV.text = getString(R.string.full_name, contact.firstName, contact.lastName)
        descriptionTV.text = contact.description
        contactIV.setImageResource(contact.pictureID)

        callBtn.setOnClickListener {
            dialPhoneNumber("+4915251741573")
        }

        messageBtn.setOnClickListener {
            textPhoneNumber("+4915251741573")
        }

        background.setOnClickListener {
            onBackPressed()
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No phone app found.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun textPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No phone app found.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_POSITION = "InterestDetailsActivity.EXTRA_POSITION"

        fun newIntent(context: Context, position: Int): Intent {
            val intent = Intent(context, InterestDetailsActivity::class.java)
            intent.putExtra(EXTRA_POSITION, position)

            return intent
        }
    }
}