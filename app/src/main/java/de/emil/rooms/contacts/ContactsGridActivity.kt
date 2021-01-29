package de.emil.rooms.contacts

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bindView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import de.emil.rooms.Data
import de.emil.rooms.R
import de.emil.rooms.RoomActivity

class ContactsGridActivity : RoomActivity() {

    private var values = Data.contactValues

    private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTransition()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun setupTransition() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // Attach a callback used to capture the shared elements from this Activity to be used
        // by the container transform transition
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        // Keep system bars (status bar, navigation bar) persistent throughout the transition.
        window.sharedElementsUseOverlay = true
    }

    private fun init() {
        val adapter = ContactAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    internal inner class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
        private val inflater: LayoutInflater = layoutInflater

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val view = inflater.inflate(R.layout.li_contact, parent, false)
            return ContactViewHolder(view)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            val contact = values[position]

            holder.name.text = contact.firstName
            holder.image.setImageResource(contact.pictureID)

            holder.itemView.setOnClickListener {
                val intent = ContactDetailsActivity.newIntent(this@ContactsGridActivity, position)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@ContactsGridActivity,
                    holder.mainLayout,
                    "shared_element_image" // The transition name to be matched in Activity B.
                )

                startActivity(intent, options.toBundle())
            }
        }

        override fun getItemCount(): Int {
            return values.size
        }

        internal inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById(R.id.cardContactTV)
            var image: ImageView = itemView.findViewById(R.id.cardContactIV)
            var mainLayout: CardView = itemView.findViewById(R.id.mainLayout)
        }
    }
}