package de.emil.rooms.interests

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bindView
import de.emil.rooms.Data
import de.emil.rooms.R
import de.emil.rooms.RoomActivity
import de.emil.rooms.model.Interest

class InterestsActivity: RoomActivity() {

    private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    private var selectedValues = ArrayList<Interest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    private fun initViews() {
        val values = intent.getIntArrayExtra(EXTRA_SELECTED_VALUES)!!

        for(i in values) {
            selectedValues.add(Data.interestsValues[i])
        }

        val adapter = InterestCategory()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    internal inner class InterestCategory : RecyclerView.Adapter<InterestCategory.InterestCategoryViewHolder>() {
        private val inflater: LayoutInflater = layoutInflater

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestCategoryViewHolder {
            val view = inflater.inflate(R.layout.li_interest, parent, false)
            return InterestCategoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: InterestCategoryViewHolder, position: Int) {
            val interest = selectedValues[position]

            holder.name.text = interest.name
            holder.image.setImageResource(interest.resID)

            holder.itemView.setOnClickListener {
                val intent = InterestsGridActivity.newIntent(this@InterestsActivity, interest.name)
                startActivity(intent)
                overridePendingTransition(R.anim.right_in, R.anim.left_out)
            }
        }

        override fun getItemCount(): Int {
            return selectedValues.size
        }

        internal inner class InterestCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById(R.id.cardContactTV)
            var image: ImageView = itemView.findViewById(R.id.cardContactIV)
        }
    }

    companion object {
        const val EXTRA_SELECTED_VALUES = "InterestsActivity.EXTRA_SELECTED_VALUES"

        fun newIntent(context: Context, selectedValues: ArrayList<Int>): Intent {
            val intent = Intent(context, InterestsActivity::class.java)

            intent.putExtra(EXTRA_SELECTED_VALUES, selectedValues.toIntArray())

            return intent
        }
    }

}