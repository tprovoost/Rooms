package de.emil.rooms.api

import de.emil.rooms.interests.ChooseInterestActivity
import de.emil.rooms.model.ServiceContact
import de.emil.rooms.professionals.ProfessionalListActivity
import io.reactivex.Observable
import retrofit2.http.GET

public interface RoomApi {

    @GET("food")
    fun getFood(): Observable<ArrayList<ServiceContact>>

    @GET("health")
    fun getHealth(): Observable<ArrayList<ServiceContact>>

    @GET("craftsmen")
    fun getCraftsmen(): Observable<ArrayList<ServiceContact>>

    companion object {
        const val API_BASE_URL = "https://601408b9b538980017568a68.mockapi.io/"
    }
}