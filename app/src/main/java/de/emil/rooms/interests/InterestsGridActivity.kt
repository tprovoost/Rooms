package de.emil.rooms.interests

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bindView
import de.emil.rooms.R
import de.emil.rooms.RoomActivity

class InterestsGridActivity : RoomActivity() {

    private var values = ArrayList<Contact>()

    private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init() {
        values.add(Contact("Kevin", R.drawable.thomas, "+4915251741573"))
        values.add(Contact("Ken" , R.drawable.darya, "+4915251741573"))
        values.add(Contact("Arthur" , R.drawable.sanya, "+4915251741573"))
        values.add(Contact("Greg", R.drawable.henrik, "+4915251741573"))

        val adapter = ContactAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    inner class Contact(var name: String,
                        var pictureID: Int,
                        var number: String)

    internal inner class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
        private val inflater: LayoutInflater = layoutInflater

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val view = inflater.inflate(R.layout.li_contact, parent, false)
            return ContactViewHolder(view)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            val contact = values[position]

            holder.name.text = contact.name
            holder.image.setImageResource(contact.pictureID)

            holder.itemView.setOnClickListener {
                dialPhoneNumber(contact.number)
            }
        }

        override fun getItemCount(): Int {
            return values.size
        }

        internal inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById(R.id.cardContactTV)
            var image: ImageView = itemView.findViewById(R.id.cardContactIV)
        }
    }

    fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No phone app found.", Toast.LENGTH_SHORT).show()
        }
    }
}