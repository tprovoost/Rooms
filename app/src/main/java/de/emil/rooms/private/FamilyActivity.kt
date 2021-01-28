package de.emil.rooms.private

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bindView
import de.emil.rooms.R
import de.emil.rooms.RoomActivity

class FamilyActivity : RoomActivity() {

    private var values = ArrayList<String>()

    private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_family)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init() {
        values.add("Thomas")
        values.add("Darya")
        values.add("Sanya")
        values.add("Henrik")

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
            val item = values[holder.adapterPosition]

            holder.name.text = item
        }

        override fun getItemCount(): Int {
            return values.size
        }

        internal inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById(R.id.cardContactTV)

        }
    }

}