package de.emil.rooms.professionals

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bindView
import de.emil.rooms.R

class ProfessionalListActivity : AppCompatActivity() {

    private var values = ArrayList<ServiceContact>()
    private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    enum class CategoriesEnum(name: String) {
        HEALTH("Health"),
        CRAFT("Craft"),
        FOOD("Food"),
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_professionals)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getSerializableExtra(EXTRA_CATEGORY)?.apply {
            val category = this as CategoriesEnum
            supportActionBar?.title = category.name
        }?: throw RuntimeException("Unknown extra")

        init()
    }

    private fun init() {
        values.add(ServiceContact("Ristorante Alfredo",
            "Very tasty italian restaurant",
            "Osolemio 3, 12345 Berlin",
            "+4915251741573",
            "100 m"))
        values.add(ServiceContact("Sushi Hero",
            "Best Sushis in Berlin",
            "Superstraße 132, 12345 Berlin",
            "+4915251741573",
            "400 m"))
        values.add(ServiceContact("Extra Burger",
            "Make Burgers Great Again",
            "Amerikanerstraße 2, 12345 Berlin",
            "+4915251741573",
            "1.3 km"))

        val adapter = ServiceContactAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    inner class ServiceContact(var name: String,
                        var description: String,
                        var address: String,
                        var number: String,
                        var distance: String)

    internal inner class ServiceContactAdapter : RecyclerView.Adapter<ServiceContactAdapter.ServiceContactViewHolder>() {
        private val inflater: LayoutInflater = layoutInflater

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceContactViewHolder {
            val view = inflater.inflate(R.layout.li_service_contact, parent, false)
            return ServiceContactViewHolder(view)
        }

        override fun onBindViewHolder(holder: ServiceContactViewHolder, position: Int) {
            val contact = values[position]

            holder.name.text = contact.name
            holder.distance.text = contact.distance
            holder.description.text = contact.description

            holder.itemView.setOnClickListener {
                dialPhoneNumber(contact.number)
            }
        }

        override fun getItemCount(): Int {
            return values.size
        }

        internal inner class ServiceContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById(R.id.cardServiceName)
            var distance: TextView = itemView.findViewById(R.id.cardServiceDistance)
            var description: TextView = itemView.findViewById(R.id.cardServiceDescription)
        }
    }

    fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No phone app found.", Toast.LENGTH_SHORT)
        }
    }

    companion object {
        private const val EXTRA_CATEGORY = "ProfessionalListActivity.EXTRA_CATEGORY"

        fun newIntent(context: Context, category: CategoriesEnum): Intent {
            Log.d("Professionals extra", "Creating intent with ${category.name}")
            val intent = Intent(context, ProfessionalListActivity::class.java)

            intent.putExtra(EXTRA_CATEGORY, category)
            return intent
        }
    }
}