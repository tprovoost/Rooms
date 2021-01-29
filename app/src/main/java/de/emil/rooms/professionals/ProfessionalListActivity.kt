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
    private val loading: View by bindView(R.id.loading)

    private lateinit var api: RoomApi
    private lateinit var category: CategoriesEnum

    enum class CategoriesEnum(var value: String) {
        HEALTH("Doctors"),
        CRAFT("Craftsmen"),
        FOOD("Restaurants"),
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_professionals)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getSerializableExtra(EXTRA_CATEGORY)?.apply {
            category = this as CategoriesEnum
            supportActionBar?.title = category.value
        }?: throw RuntimeException("Unknown extra")

        init()
    }

    private fun init() {
        initService()

        val observable = when(category) {
            CategoriesEnum.FOOD -> api.getFood()
            CategoriesEnum.HEALTH -> api.getHealth()
            CategoriesEnum.CRAFT -> api.getCraftsmen()
        }

        loading.visibility = View.VISIBLE

        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                values.clear()
                values.addAll(it)
                recyclerView.adapter?.notifyDataSetChanged()
                loading.visibility = View.GONE
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

            holder.tvName.text = contact.name

            holder.tvDistance.text = "${contact.distance} km"
            holder.tvAddress.text = contact.address

            if (contact.description.isNullOrEmpty()) {
                holder.separator.visibility = View.GONE
                holder.tvDescription.visibility = View.INVISIBLE
            } else {
                holder.tvDescription.visibility = View.VISIBLE
                holder.tvDescription.text = contact.description
            }

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
            var tvName: TextView = itemView.findViewById(R.id.cardServiceName)
            var tvDescription: TextView = itemView.findViewById(R.id.cardServiceDescription)
            var tvDistance: TextView = itemView.findViewById(R.id.cardServiceDistance)
            var tvAddress: TextView = itemView.findViewById(R.id.cardServiceAddress)
            var separator: View = itemView.findViewById(R.id.separator)
        }
    }

    fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:+$phoneNumber")
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