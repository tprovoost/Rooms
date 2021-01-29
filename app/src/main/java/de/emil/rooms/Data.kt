package de.emil.rooms

import de.emil.rooms.model.Contact
import de.emil.rooms.model.Interest
import de.emil.rooms.model.InterestContact

object Data {

    var contactValues: ArrayList<Contact> = ArrayList()
    var interestsValues: ArrayList<Interest> = ArrayList()
    var interestsContactValues: ArrayList<InterestContact> = ArrayList()

    init {
        contactValues.add(Contact("Thomas", "Provoost", R.drawable.thomas, "+4915251741573"))
        contactValues.add(Contact("Darya", "Prokopova", R.drawable.darya, "+4915251741573"))
        contactValues.add(Contact("Tobi", "Sanya", R.drawable.sanya, "+4915251741573"))
        contactValues.add(Contact("Henrik", "Dittmar", R.drawable.henrik, "+4915251741573"))

        interestsContactValues.add(InterestContact("Kevin", "Spacey", R.drawable.spacey, "+4915251741573", "Travel, Food, Cinema"))
        interestsContactValues.add(InterestContact("Amy" , "Adams", R.drawable.adams, "+4915251741573", "Travel, Cinema, Family, TV"))
        interestsContactValues.add(InterestContact("Morgan" , "Freeman", R.drawable.freeman, "+4915251741573", "Travel, Food, Wine"))
        interestsContactValues.add(InterestContact("James", "Bond", R.drawable.bond, "+4915251741573", "Travel, Murder"))

        interestsValues.add(Interest("Sport", R.drawable.interest_sport))
        interestsValues.add(Interest("Music", R.drawable.interest_music))
        interestsValues.add(Interest("Meditation", R.drawable.interest_meditation))
        interestsValues.add(Interest("Pets", R.drawable.interest_pets))
        interestsValues.add(Interest("Travel", R.drawable.interest_travel))
        interestsValues.add(Interest("Technology", R.drawable.interest_technology))
        interestsValues.add(Interest("Science", R.drawable.interest_science))
        interestsValues.add(Interest("Art", R.drawable.interest_painter))
        interestsValues.add(Interest("Writing", R.drawable.interest_writing))
        interestsValues.add(Interest("Mood booster", R.drawable.interest_moodbooster))
    }

}