package de.emil.rooms.interests

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bindView
import de.emil.rooms.Data
import de.emil.rooms.R
import de.emil.rooms.RoomActivity

class ChooseInterestActivity : RoomActivity() {

    private var values = Data.interestsValues
    private var selectedValues = ArrayList<Int>()

    private val recyclerView: RecyclerView by bindView(R.id.recyclerView)
    private val confirmBtn: View by bindView(R.id.confirmBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_interest)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init() {
        val adapter = InterestAdapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        confirmBtn.setOnClickListener {
            val intent = InterestsActivity.newIntent(this, selectedValues)
            startActivity(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
        confirmBtn.isEnabled = false
    }

    internal inner class InterestAdapter : RecyclerView.Adapter<InterestAdapter.InterestViewHolder>() {
        private val inflater: LayoutInflater = layoutInflater

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
            val view = inflater.inflate(R.layout.li_interest_contact, parent, false)
            return InterestViewHolder(view)
        }

        override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
            val contact = values[position]

            holder.name.text = contact.name
            holder.image.setImageResource(contact.resID)
            holder.selectedView.visibility = View.GONE

            holder.itemView.setOnClickListener {
                if (holder.selectedView.visibility == View.GONE) {
                    holder.selectedView.visibility = View.VISIBLE
                    selectedValues.add(position)
                } else {
                    holder.selectedView.visibility = View.GONE
                    selectedValues.remove(position)
                }
                if (selectedValues.isEmpty()) {
                    confirmBtn.setBackgroundColor(getColor(R.color.colorAccent))
                    confirmBtn.isEnabled = false
                } else {
                    confirmBtn.setBackgroundColor(getColor(R.color.selectedBlue))
                    confirmBtn.isEnabled = true
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