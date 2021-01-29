package de.emil.rooms.model

import com.google.gson.annotations.SerializedName

data class ServiceContact(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("address") var address: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("distance") var distance: Double)


data class Contact(var firstName: String,
                   var lastName: String,
                   var pictureID: Int,
                   var number: String)

data class InterestContact(var firstName: String,
                           var lastName: String,
                           var pictureID: Int,
                           var number: String,
                           var description: String)

data class Interest(var name: String, var resID: Int)