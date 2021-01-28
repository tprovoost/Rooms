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
import de.emil.rooms.RoomActivity
import de.emil.rooms.api.RoomApi
import de.emil.rooms.model.ServiceContact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ProfessionalListActivity : RoomActivity() {

    private var values = ArrayList<ServiceContact>()
    private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    private lateinit var api: RoomApi

    enum class CategoriesEnum(value: String) {
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
        /*values.add(ServiceContact(1, "Ristorante Alfredo",
            "Very tasty italian restaurant",
            "Osolemio 3, 12345 Berlin",
            "+4915251741573",
            "100 m"))
        values.add(ServiceContact(2, "Sushi Hero",
            "Best Sushis in Berlin",
            "Superstraße 132, 12345 Berlin",
            "+4915251741573",
            "400 m"))
        values.add(
            ServiceContact(3, "Extra Burger",
            "Make Burgers Great Again",
            "Amerikanerstraße 2, 12345 Berlin",
            "+4915251741573",
            "1.3 km")
        )*/

        initService()

        api.getFood()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                values.addAll(it)
                recyclerView.adapter?.notifyDataSetChanged()
            }
            .doOnError {
                Log.e("ProfessionalList", "Failure on call", it)
            }
            .subscribe()

        val adapter = ServiceContactAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initService() {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(cacheDir, cacheSize.toLong())

        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(RoomApi.API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        api = retrofit.create(RoomApi::class.java)
    }

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
                if (contact.phone.isEmpty()) {
                    Toast.makeText(this@ProfessionalListActivity, "No phone number found.", Toast.LENGTH_SHORT)
                        .show()
                }
                dialPhoneNumber(contact.phone)
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
            Toast.makeText(this, "No phone app found.", Toast.LENGTH_SHORT).show()
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