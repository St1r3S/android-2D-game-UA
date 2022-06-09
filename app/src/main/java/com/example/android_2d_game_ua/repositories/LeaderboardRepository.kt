package com.example.android_2d_game_ua.repositories

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LeaderboardRepository {
    private val database: DatabaseReference =
        Firebase.database("https://android-2d-game-ua-ff466-default-rtdb.europe-west1.firebasedatabase.app/").reference
    fun getLeaders(): DatabaseReference {
        return database.child("Users")
    }
}