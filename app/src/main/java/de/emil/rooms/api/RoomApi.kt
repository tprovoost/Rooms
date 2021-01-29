package de.emil.rooms.api

import de.emil.rooms.model.ServiceContact
import io.reactivex.Observable
import retrofit2.http.GET

public interface RoomApi {

    @GET("restaurants")
    fun getFood(): Observable<ArrayList<ServiceContact>>

    @GET("health-officials")
    fun getHealth(): Observable<ArrayList<ServiceContact>>

    @GET("craftsmen")
    fun getCraftsmen(): Observable<ArrayList<ServiceContact>>

    companion object {
        const val API_BASE_URL = "https://601408b9b538980017568a68.mockapi.io/"
    }
}