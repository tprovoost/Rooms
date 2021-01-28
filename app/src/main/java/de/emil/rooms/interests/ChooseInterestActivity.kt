package de.emil.rooms.interests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bindView
import de.emil.rooms.R
import de.emil.rooms.RoomActivity
import de.emil.rooms.private.ContactsGridActivity

class ChooseInterestActivity : RoomActivity() {

    private var values = ArrayList<Interest>()
    private var selectedValues = ArrayList<Int>()

    private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_interest)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init() {
        values.add(Interest("Travel", R.drawable.thomas))
        values.add(Interest("Card games" , R.drawable.darya))
        values.add(Interest("Video Games" , R.drawable.sanya))
        values.add(Interest("Football", R.drawable.henrik))
        values.add(Interest("Rugby", R.drawable.henrik))


        val adapter = InterestAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
    }

    inner class Interest(var name: String, var pictureID: Int)

    internal inner class InterestAdapter : RecyclerView.Adapter<InterestAdapter.InterestViewHolder>() {
        private val inflater: LayoutInflater = layoutInflater

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
            val view = inflater.inflate(R.layout.li_interest, parent, false)
            return InterestViewHolder(view)
        }

        override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
            val contact = values[position]

            holder.name.text = contact.name
            holder.image.setImageResource(R.drawable.sanya)
            holder.selectedView.visibility = View.GONE

            holder.itemView.setOnClickListener {
                if (holder.selectedView.visibility == View.GONE) {
                    holder.selectedView.visibility = View.VISIBLE
                    selectedValues.add(position)
                } else {
                    holder.selectedView.visibility = View.GONE
                    selectedValues.remove(position)
                }
            }
        }

        override fun getItemCount(): Int {
            return values.size
        }

        internal inner class InterestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById(R.id.cardContactTV)
            var image: ImageView = itemView.findViewById(R.id.cardContactIV)
            var selectedView: View = itemView.findViewById(R.id.selectedView)
        }
    }

}