package com.example.android_2d_game_ua.repositories

import com.example.android_2d_game_ua.model.user.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {
    private val database: DatabaseReference =
        Firebase.database("https://android-2d-game-ua-ff466-default-rtdb.europe-west1.firebasedatabase.app/").reference

    fun getUserByUserId(uid: String): DatabaseReference {
        return database.child("Users").child(uid)
    }

    fun setScore(uid: String, score: Long) {
        database.child("Users").child(uid).child("score").setValue(score)
    }

    fun createUser(user: User) {
        database.child("Users").child(user.userId.toString()).setValue(user)
    }
}